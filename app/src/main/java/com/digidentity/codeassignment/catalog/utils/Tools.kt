package com.digidentity.codeassignment.catalog.utils

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.digidentity.codeassignment.catalog.R


fun loadImage(url: String, imageView: AppCompatImageView) {
    Glide.with(imageView.context).load(url)
        .placeholder(R.drawable.ic_launcher_foreground)
        .error(R.drawable.ic_error)
        .into(imageView)
}

/**
 * There is a rule for building an image url witch need to be respected
 */
fun buildImageUrl(
    baseImageUrl: String = Constants.BASE_IMAGE_URL,
    imageSize: String = Constants.IMAGE_SIZE_512x512,
    text: String
) = "$baseImageUrl$imageSize${Constants.TEXT_URL}$text"


/**
 *  We assume there is two type of ids for each item.
 *  SinceID for fetching recent items than this item
 *  MaxID for fetching older items than this item
 */
sealed class ItemId(val value: String) {

    class SinceID(value: String) : ItemId(value)

    class MaxID(value: String) : ItemId(value)

}

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}


