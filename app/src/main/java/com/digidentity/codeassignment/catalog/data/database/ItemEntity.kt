package com.digidentity.codeassignment.catalog.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.digidentity.codeassignment.catalog.domain.model.Item

@Entity(tableName = "catalog_items")
data class ItemEntity(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = false) val id: String,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "confidence") val confidence: Double,
    @ColumnInfo(name = "img") val image: String
)

fun ItemEntity.toItem() = Item(id, text, image, confidence)