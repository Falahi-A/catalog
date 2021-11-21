package com.digidentity.codeassignment.catalog.presentation.main.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.digidentity.codeassignment.catalog.R
import com.digidentity.codeassignment.catalog.databinding.AddNewItemDialogViewBinding
import com.digidentity.codeassignment.catalog.databinding.FragmentItemsBinding
import com.digidentity.codeassignment.catalog.domain.model.Item
import com.digidentity.codeassignment.catalog.domain.model.NewItem
import com.digidentity.codeassignment.catalog.presentation.base.BaseBindingFragment
import com.digidentity.codeassignment.catalog.presentation.main.MainActivity
import com.digidentity.codeassignment.catalog.utils.ItemId
import com.digidentity.codeassignment.catalog.utils.buildImageUrl
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
            ItemsFragmentDirections.actionItemsFragmentToItemDetailsFragment(item)
        findNavController().navigate(action)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentItemsBinding =
        { layoutInflater, viewGroup, attachedToParent ->
            FragmentItemsBinding.inflate(layoutInflater, viewGroup, attachedToParent)
        }

    override fun initView() {

        binding.recyclerItems.adapter = adapter

        recyclerScrollListener()


        observeItemsViewState()
        observeNewItemViewState()


        binding.btnPlus.setOnClickListener {
            showAddNewItemDialog()
        }

    }

    private fun observeNewItemViewState() {
        viewModel.newItem.observe(viewLifecycleOwner, { newItemViewState ->

            when {

                newItemViewState.loading -> { // loading data
                    (activity as MainActivity).displayProgress(true)
                }

                newItemViewState.newItem != null -> { // data handling. new item was added to catalog
                    (activity as MainActivity).displayProgress(false)
                    (activity as MainActivity).displayMessage("${newItemViewState.newItem.text} was added to catalog")

                }

                newItemViewState.errorMessage.isNotEmpty() -> {   // error handling
                    (activity as MainActivity).displayProgress(false)
                    (activity as MainActivity).displayMessage(newItemViewState.errorMessage)
                }

            }

        })
    }

    private fun observeItemsViewState() {
        viewModel.items.observe(viewLifecycleOwner, { itemsViewState ->

            when {

                itemsViewState.loading -> { // loading data
                    (activity as MainActivity).displayProgress(true)
                }

                itemsViewState.data.isNotEmpty() -> { // items handling. when catalog has items
                    (activity as MainActivity).displayProgress(false)
                    adapter.submitList(itemsViewState.data)
                }
                itemsViewState.data.isEmpty() && itemsViewState.errorMessage.isEmpty() -> { // items empty handling. when catalog has no items
                    (activity as MainActivity).displayProgress(false)
                    (activity as MainActivity).displayMessage(requireContext().getString(R.string.no_items_message))

                }

                itemsViewState.errorMessage.isNotEmpty() -> {   // error handling
                    (activity as MainActivity).displayProgress(false)
                    (activity as MainActivity).displayMessage(itemsViewState.errorMessage)
                }

            }

        })

    }

    private fun recyclerScrollListener() {
        binding.recyclerItems.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //reached end
                    val lastItem = adapter.currentList.last() // last list item
                    viewModel.getItems(ItemId.MaxID(lastItem.id)) // fetching next items
                }

                if (!recyclerView.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // reached top
                    val firstItem = adapter.currentList.first()  // first list item
                    viewModel.getItems(ItemId.SinceID(firstItem.id)) // fetching most recent items

                }

            }
        })
    }

    private fun showAddNewItemDialog() {
        val binding = AddNewItemDialogViewBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext()).setView(binding.root)
            .setTitle(R.string.new_catalog_item).setCancelable(false).create()

        binding.btnCancelItemAddDialog.setOnClickListener {
            dialog.dismiss()
        }
        binding.btnInsertItemAddDialog.setOnClickListener {
            val itemText = binding.editTextItemAddDialog.text.toString()
            val itemConfidence = binding.editConfidenceItemAddDialog.text.toString()
            if (!itemText.isNullOrEmpty() && !itemConfidence.isNullOrEmpty()) {    // check if dialog item texts are filled or not
                viewModel.addNewItem(
                    NewItem(
                        text = itemText,
                        confidence = itemConfidence.toDouble(),
                        image = buildImageUrl(text = itemText)
                    )
                )

                dialog.dismiss()
            } else {
                (activity as MainActivity).displayMessage(requireContext().getString(R.string.fill_the_fields))
            }
        }

        dialog.show()
    }


}