package com.digidentity.codeassignment.catalog.presentation.main.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.digidentity.codeassignment.catalog.databinding.FragmentItemsBinding
import com.digidentity.codeassignment.catalog.domain.model.Item
import com.digidentity.codeassignment.catalog.presentation.base.BaseBindingFragment
import com.digidentity.codeassignment.catalog.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Display Catalog Items
 */
@AndroidEntryPoint
class ItemsFragment : BaseBindingFragment<FragmentItemsBinding>() {

    private val viewModel: ItemsViewModel by viewModels()

    private val adapter: ItemsAdapter by lazy {
        ItemsAdapter { item ->
            goToItemDetails(item)
        }
    }

    private fun goToItemDetails(item: Item) {
        val action =
            ItemsFragmentDirections.actionItemsFragmentToItemDetailsFragment2(item)
        findNavController().navigate(action)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentItemsBinding =
        { layoutInflater, viewGroup, attachedToParent ->
            FragmentItemsBinding.inflate(layoutInflater, viewGroup, attachedToParent)
        }

    override fun initView() {

        binding.recyclerItems.adapter = adapter

        viewModel.getItems()

        viewModel.items.observe(viewLifecycleOwner, { viewState ->

            when {

                viewState.loading -> { // loading data
                    (activity as MainActivity).displayProgress(true)
                }

                viewState.data != null -> { // data handling
                    (activity as MainActivity).displayProgress(false)
                    adapter.submitList(viewState.data)
                }

                viewState.errorMessage != null -> {   // error handling
                    (activity as MainActivity).displayProgress(false)
                    (activity as MainActivity).displayMessage(viewState.errorMessage)
                }
            }

        })

    }


}