package com.digidentity.codeassignment.catalog.presentation.main.item_details

import android.view.LayoutInflater
import android.view.ViewGroup
import com.digidentity.codeassignment.catalog.databinding.FragmentItemDetailsBinding
import com.digidentity.codeassignment.catalog.presentation.base.BaseBindingFragment

/**
 *  Display Catalog Item Details
 */
class ItemDetailsFragment : BaseBindingFragment<FragmentItemDetailsBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentItemDetailsBinding =
        { layoutInflater, viewGroup, attachedToParent ->
            FragmentItemDetailsBinding.inflate(layoutInflater, viewGroup, attachedToParent)
        }

    override fun initView() {

    }


}