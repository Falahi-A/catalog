package com.digidentity.codeassignment.catalog.domain.model

import com.google.gson.Gson

data class NewItem(val text: String, val confidence: Double, val image: String)

fun NewItem.toJson(): String =
    Gson().toJson(this)