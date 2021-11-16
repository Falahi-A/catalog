package com.digidentity.codeassignment.catalog.domain.usecase

import com.digidentity.codeassignment.catalog.data.network.model.toItem
import com.digidentity.codeassignment.catalog.domain.model.Item
import com.digidentity.codeassignment.catalog.domain.repository.CatalogRepository
import com.digidentity.codeassignment.catalog.utils.Constants
import com.digidentity.codeassignment.catalog.utils.ItemId
import com.digidentity.codeassignment.catalog.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named

class GetCatalogItemsUseCase @Inject constructor(
    private val catalogRepository: CatalogRepository,
    @Named(Constants.IO_DISPATCHER) private val ioDispatcher: CoroutineDispatcher
) {


    operator fun invoke(itemId: ItemId? = null) = flow<Resource<List<Item>>> {
        try {
            emit(Resource.Loading())
            val list = when (itemId) {

                is ItemId.SinceID -> {
                    catalogRepository.getItemsSinceId(itemId.value)
                }
                is ItemId.MaxID -> {
                    catalogRepository.getItemsMaxId(itemId.value)
                }
                else -> {
                    catalogRepository.getRecentItems()
                }

            }

            emit(Resource.Success(list.map {
                it.toItem()
            }))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }


    }.flowOn(ioDispatcher)



}
