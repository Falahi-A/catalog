package com.digidentity.codeassignment.catalog.presentation.main.items

import androidx.lifecycle.*
import com.digidentity.codeassignment.catalog.domain.model.NewItem
import com.digidentity.codeassignment.catalog.domain.usecase.AddCatalogNewItemUseCase
import com.digidentity.codeassignment.catalog.domain.usecase.GetCatalogItemsUseCase
import com.digidentity.codeassignment.catalog.utils.Event
import com.digidentity.codeassignment.catalog.utils.ItemId
import com.digidentity.codeassignment.catalog.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val getCatalogItemsUseCase: GetCatalogItemsUseCase,
    private val addCatalogNewItemUseCase: AddCatalogNewItemUseCase
) :
    ViewModel() {

    private val _items = MutableLiveData<ItemsViewState>()
    val items: LiveData<ItemsViewState> = _items

    private val _newItem = MutableLiveData<Event<NewItemViewState>>()
    val newItem: LiveData<Event<NewItemViewState>> = _newItem


//    init {
//        getItems()
//    }

    fun getItems(itemId: ItemId? = null) {
        getCatalogItemsUseCase(itemId).onEach { result ->

            when (result) {
                is Resource.Loading -> {
                    _items.value = ItemsViewState(loading = true)

                }
                is Resource.Success -> {
                    _items.value = ItemsViewState(data = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _items.value = ItemsViewState(
                        errorMessage = result.message ?: "an unexpected error happened"
                    )

                }
            }

        }.launchIn(viewModelScope)
    }

    fun addNewItem(item: NewItem) {
        addCatalogNewItemUseCase(item).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _newItem.value = Event(NewItemViewState(loading = true))

                }
                is Resource.Success -> {
                    _newItem.value = Event(NewItemViewState(newItem = result.data))
                }
                is Resource.Error -> {
                    _newItem.value = Event(
                        NewItemViewState(
                            errorMessage = result.message ?: "an unexpected error happened"
                        )
                    )

                }
            }

        }.launchIn(viewModelScope)
    }
}