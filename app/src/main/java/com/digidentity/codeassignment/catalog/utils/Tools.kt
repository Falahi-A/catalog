package com.digidentity.codeassignment.catalog.utils

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.digidentity.codeassignment.catalog.R


fun loadImage(url: String, imageView: AppCompatImageView) {
    Glide.with(imageView.context).load(url)
        .placeholder(R.drawable.ic_launcher_foreground)
        .into(imageView)
}


sealed class ItemId(val value: String) {

    class SinceID(value: String) : ItemId(value)

    class MaxID(value: String) : ItemId(value)

}



