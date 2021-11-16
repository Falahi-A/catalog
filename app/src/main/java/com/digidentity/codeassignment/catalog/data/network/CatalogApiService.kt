package com.digidentity.codeassignment.catalog.data.network

import com.digidentity.codeassignment.catalog.data.network.model.ItemNetResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CatalogApiService {

    // ======== get catalog items ========
    @GET("items")
    fun getItems(): List<ItemNetResponse>

    // ======== get catalog items more recent than specified id ========
    @GET("items")
    fun getItemsSince(@Query("since_id") sinceId: String): List<ItemNetResponse>

    // ======== get catalog items less or equal than specified id ========
    @GET("items")
    fun getItemsMax(@Query("max_id") maxId: String): List<ItemNetResponse>

    // ======== add new item ========
    @POST("items")
    fun addNewItem(@Body item: String): ItemNetResponse

}