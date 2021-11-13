package com.digidentity.codeassignment.catalog.data.network

import com.digidentity.codeassignment.catalog.data.network.model.ItemNetResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CatalogApiService {

    @GET("items")
    fun getItems(): List<ItemNetResponse>

    @POST("items")
    fun addNewItem(@Body item: String): ItemNetResponse

}