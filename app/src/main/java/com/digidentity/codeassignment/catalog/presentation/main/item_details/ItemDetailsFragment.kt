package com.digidentity.codeassignment.catalog.presentation.main.item_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.digidentity.codeassignment.catalog.R
import com.digidentity.codeassignment.catalog.databinding.FragmentItemDetailsBinding
import com.digidentity.codeassignment.catalog.presentation.base.BaseBindingFragment
import com.digidentity.codeassignment.catalog.utils.loadImage

/**
 *  Display Catalog Item Details
 */
class ItemDetailsFragment : BaseBindingFragment<FragmentItemDetailsBinding>() {


    private val args: ItemDetailsFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentItemDetailsBinding =
        { layoutInflater, viewGroup, attachedToParent ->
            FragmentItemDetailsBinding.inflate(layoutInflater, viewGroup, attachedToParent)
        }

    override fun initView() {

        val itemDetails = args.item

        binding.txtItemTextDetails.text =
            context?.getString(R.string.text).plus(": ").plus(itemDetails.text)
        binding.txtItemIdDetails.text =
            context?.getString(R.string.id).plus(": ").plus(itemDetails.id)
        binding.txtItemConfidenceDetails.text =
            context?.getString(R.string.confidence).plus(": ")
                .plus(itemDetails.confidence.toString())

        loadImage(itemDetails.image, binding.imgItemDetails)



    }
}