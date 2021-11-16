package com.digidentity.codeassignment.catalog.data.repository

import com.digidentity.codeassignment.catalog.data.network.CatalogApiService
import com.digidentity.codeassignment.catalog.data.network.model.ItemNetResponse
import com.digidentity.codeassignment.catalog.domain.repository.CatalogRepository
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(private val catalogApiService: CatalogApiService) :
    CatalogRepository {


    override suspend fun getRecentItems(): List<ItemNetResponse> {
        return catalogApiService.getItems()
    }

    override suspend fun getItemsSinceId(sinceId: String): List<ItemNetResponse> {
        return catalogApiService.getItemsSince(sinceId)
    }

    override suspend fun getItemsMaxId(maxId: String): List<ItemNetResponse> {
        return catalogApiService.getItemsMax(maxId)
    }

    override suspend fun addNewItem(newItem: String): ItemNetResponse {
        return catalogApiService.addNewItem(newItem)
    }

}