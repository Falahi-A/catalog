package com.digidentity.codeassignment.catalog.presentation.main.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.digidentity.codeassignment.catalog.R
import com.digidentity.codeassignment.catalog.databinding.ItemViewBinding
import com.digidentity.codeassignment.catalog.domain.model.Item
import com.digidentity.codeassignment.catalog.presentation.base.BaseViewHolder

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
            viewItem.textItem.text = itemView.context.getString(R.string.text).plus(": ").plus(obj.text)
            viewItem.textIdItem.text = itemView.context.getString(R.string.id).plus(": ").plus(obj.id)
            viewItem.textConfidenceItem.text =
                itemView.context.getString(R.string.confidence).plus(": ").plus(obj.confidence.toString())
            viewItem.root.setOnClickListener {
                onItemClick(obj)
            }

        }


    }

    object DiffConfig : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }


}