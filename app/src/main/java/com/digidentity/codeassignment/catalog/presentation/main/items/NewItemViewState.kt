package com.digidentity.codeassignment.catalog.presentation.main.items

import com.digidentity.codeassignment.catalog.domain.model.Item

data class NewItemViewState(
    val loading: Boolean = false,
    val newItem: Item? = null,
    val errorMessage: String = ""
)