package com.digidentity.codeassignment.catalog.presentation.main.items

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digidentity.codeassignment.catalog.domain.usecase.GetCatalogItemsUseCase
import com.digidentity.codeassignment.catalog.utils.ItemId
import com.digidentity.codeassignment.catalog.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(private val getCatalogItemsUseCase: GetCatalogItemsUseCase) :
    ViewModel() {

    private val _items = MutableLiveData<ItemsViewState>()
    val items: LiveData<ItemsViewState> = _items


    fun getItems(itemId: ItemId? = null) {
        getCatalogItemsUseCase(itemId).onEach { result ->

            when (result) {
                is Resource.Loading -> {
                    _items.value = ItemsViewState(loading = true)

                }
                is Resource.Success -> {
                    _items.value = ItemsViewState(data = result.data)
                }
                is Resource.Error -> {
                    _items.value = ItemsViewState(errorMessage = result.message)

                }
            }

        }.launchIn(viewModelScope)
    }


}