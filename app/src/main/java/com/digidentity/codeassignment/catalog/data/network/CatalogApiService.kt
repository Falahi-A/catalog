package com.digidentity.codeassignment.catalog.data.network

import com.digidentity.codeassignment.catalog.data.network.model.ItemNetResponse
import retrofit2.http.*

interface CatalogApiService {

    // ======== get catalog most recent items ========
    @GET("items")
    suspend fun getItems(): List<ItemNetResponse>

    // ======== get catalog more recent than specified id items ========
    @GET("items")
    suspend fun getItemsSince(@Query("since_id") sinceId: String): List<ItemNetResponse>

    // ======== get catalog less or equal than specified id items ========
    @GET("items")
    suspend fun getItemsMax(@Query("max_id") maxId: String): List<ItemNetResponse>

    // ======== add new item to catalog ========
    @POST("items")
    suspend fun addNewItem(@Body newItem: String): ItemNetResponse

}