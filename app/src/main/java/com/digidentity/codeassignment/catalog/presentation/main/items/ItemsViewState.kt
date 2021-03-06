package com.digidentity.codeassignment.catalog.presentation.main.items

import com.digidentity.codeassignment.catalog.domain.model.Item

data class ItemsViewState(
    val data: List<Item> = emptyList(),
    val loading: Boolean = false,
    val errorMessage: String = "",
)