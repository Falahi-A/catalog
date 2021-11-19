package com.digidentity.codeassignment.catalog.data.network.model

import com.digidentity.codeassignment.catalog.data.database.ItemEntity
import com.digidentity.codeassignment.catalog.domain.model.Item
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ItemNetResponse(
    @Json(name = "text") val text: String,
    @Json(name = "image") val image: String,
    @Json(name = "confidence") val confidence: Double,
    @Json(name = "_id") val id: String
)

fun ItemNetResponse.toItemEntity(): ItemEntity {
    return ItemEntity(id = id, text = text, image = image , confidence = confidence)
}
fun ItemNetResponse.toItem(): Item {
    return Item(id = id, text = text, image = image , confidence = confidence)
}