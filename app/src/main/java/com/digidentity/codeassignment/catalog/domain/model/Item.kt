package com.digidentity.codeassignment.catalog.domain.model

import com.google.gson.Gson

data class Item(val text: String, val image: String, val confidence: String)


fun Item.toJson():String=
    Gson().toJson(this)