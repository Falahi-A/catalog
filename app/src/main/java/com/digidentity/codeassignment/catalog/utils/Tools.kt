package com.digidentity.codeassignment.catalog.utils

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.digidentity.codeassignment.catalog.R


fun loadImage(url: String, imageView: AppCompatImageView) {
    Glide.with(imageView.context).load(url)
        .placeholder(R.drawable.ic_launcher_foreground)
        .into(imageView)
}

fun buildImageUrl(
    baseImageUrl: String = Constants.BASE_IMAGE_URL,
    imageSize: String = Constants.IMAGE_SIZE_512x512,
    text: String
) =
    "$baseImageUrl$imageSize${Constants.TEXT_URL}$text"

sealed class ItemId(val value: String) {

    class SinceID(value: String) : ItemId(value)

    class MaxID(value: String) : ItemId(value)

}



