package com.digidentity.codeassignment.catalog.domain.repository

import com.digidentity.codeassignment.catalog.data.database.ItemEntity
import com.digidentity.codeassignment.catalog.data.network.model.ItemNetResponse
import com.digidentity.codeassignment.catalog.domain.model.NewItem
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {

    /**
     * return ten most recent items
     */
    suspend fun getRecentItems(): List<ItemNetResponse>

    /**
     * return ten items most recent than specified item id
     * @param sinceId item id
     */
    suspend fun getItemsSinceId(sinceId: String): List<ItemNetResponse>

    /**
     * return ten items older than specified item id
     * @param maxId item id
     */
    suspend fun getItemsMaxId(maxId: String): List<ItemNetResponse>

    /**
     * @param newItem a json string
     */
    suspend fun addNewItem(newItem: String): ItemNetResponse

    suspend fun deleteAllItemsDb()

    suspend fun insertItemsDb(items: List<ItemEntity>)


    suspend fun getAllItemsDb(): List<ItemEntity>


}