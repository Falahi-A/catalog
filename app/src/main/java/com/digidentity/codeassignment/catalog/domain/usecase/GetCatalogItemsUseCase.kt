package com.digidentity.codeassignment.catalog.domain.usecase

import com.digidentity.codeassignment.catalog.data.database.toItem
import com.digidentity.codeassignment.catalog.data.network.model.toItemEntity
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
            when (itemId) {
                is ItemId.SinceID -> {
                    val sinceItems =
                        catalogRepository.getItemsSinceId(itemId.value) // the most recent items are being fetched from server
                    if (sinceItems.isNotEmpty()) // if it's not empty, items are being inserted to the database
                        catalogRepository.insertItemsDb(sinceItems.map { itemNetResponse -> itemNetResponse.toItemEntity() })
                }
                is ItemId.MaxID -> {
                    val maxItems =
                        catalogRepository.getItemsMaxId(itemId.value)  // the next items are being fetched from server
                    if (maxItems.isNotEmpty()) // if it's not empty, items are being inserted to database
                        catalogRepository.insertItemsDb(maxItems.map { itemNetResponse -> itemNetResponse.toItemEntity() })
                }
                else -> { // it means, this is the first time the app is launched for fetching items
                    val recentItems =
                        catalogRepository.getRecentItems() // getting recent items form server
                    catalogRepository.deleteAllItemsDb()  // delete all previous items from database
                    catalogRepository.insertItemsDb(recentItems.map { itemNetResponse -> itemNetResponse.toItemEntity() }) // inserting recent items to database
                }
            }

            emit(Resource.Success(data = catalogRepository.getAllItemsDb().map { itemEntity ->
                itemEntity.toItem()
            }))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }


    }.flowOn(ioDispatcher)


}
