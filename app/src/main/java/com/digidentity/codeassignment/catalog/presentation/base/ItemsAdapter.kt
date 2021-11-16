package com.digidentity.codeassignment.catalog.presentation.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.digidentity.codeassignment.catalog.databinding.ItemViewBinding
import com.digidentity.codeassignment.catalog.domain.model.Item

class ItemsAdapter(private val onItemClick: (Item) -> Unit) :
    ListAdapter<Item, ItemsAdapter.ItemsViewHolder>(DiffConfig) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        return ItemsViewHolder(
            ItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }


    inner class ItemsViewHolder(private val viewItem: ItemViewBinding) :
        BaseViewHolder<Item>(viewItem.root) {

        override fun onBind(obj: Item) {
            viewItem.textItem.text = obj.text
            viewItem.textIdItem.text = obj.id
            viewItem.textConfidenceItem.text = obj.confidence.toString()
            viewItem.root.setOnClickListener {
                onItemClick(obj)
            }
        }

    }

    object DiffConfig : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }


}