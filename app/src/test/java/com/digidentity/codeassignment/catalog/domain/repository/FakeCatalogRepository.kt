package com.digidentity.codeassignment.catalog.domain.repository

import android.database.sqlite.SQLiteException
import com.digidentity.codeassignment.catalog.data.database.ItemEntity
import com.digidentity.codeassignment.catalog.data.network.model.ItemNetResponse
import com.digidentity.codeassignment.catalog.data.network.model.toItem
import com.digidentity.codeassignment.catalog.utils.buildImageUrl
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class FakeCatalogRepository : CatalogRepository {

    private val itemsList = ArrayList<ItemNetResponse>()
    private val itemsListDb = ArrayList<ItemEntity>()
    private var isNetAvailable = false // // simulating internet situation
    private var size: Int = 0 // size of items list
    private var hasSqliteException = false // simulating sqliteException
    private var hasHttpException = false // simulating httpException

    fun hasSqliteException(hasException: Boolean) {
        hasSqliteException = hasException
    }

    fun hasHttpException(hasException: Boolean) {
        hasHttpException = hasException
    }


    fun setListSize(size: Int) {
        this.size = size
    }

    fun isNetworkAvailable(isNetAvailable: Boolean) {
        this.isNetAvailable = isNetAvailable
    }

    @Throws(HttpException::class, IOException::class)
    override suspend fun getRecentItems(): List<ItemNetResponse> {
        return when {
            !hasHttpException && isNetAvailable -> createItemsList()
            hasHttpException && isNetAvailable -> throw HttpException(
                Response.error<Any>(
                    409, ResponseBody.create(
                        MediaType.parse("plain/text"), "An unexpected error happened"
                    )
                )
            )
            !isNetAvailable -> throw IOException("An unexpected error happened")
            else -> emptyList()
        }
    }

    @Throws(HttpException::class, IOException::class)
    override suspend fun getItemsSinceId(sinceId: String): List<ItemNetResponse> {
        return when {
            !hasHttpException && isNetAvailable -> createItemsList()
            hasHttpException && isNetAvailable -> throw HttpException(
                Response.error<Any>(
                    409, ResponseBody.create(
                        MediaType.parse("plain/text"), "An unexpected error happened"
                    )
                )
            )
            !isNetAvailable -> throw IOException("An unexpected error happened")
            else -> emptyList()
        }
    }

    @Throws(HttpException::class, IOException::class)
    override suspend fun getItemsMaxId(maxId: String): List<ItemNetResponse> {

        return when {
            !hasHttpException && isNetAvailable -> createItemsList()
            hasHttpException && isNetAvailable -> throw HttpException(
                Response.error<Any>(
                    409, ResponseBody.create(
                        MediaType.parse("plain/text"), "An unexpected error happened"
                    )
                )
            )
            !isNetAvailable -> throw IOException("An unexpected error happened")
            else -> emptyList()
        }
    }

    @Throws(HttpException::class, IOException::class)
    override suspend fun addNewItem(newItem: String): ItemNetResponse {
        return when {
            hasHttpException && isNetAvailable -> throw HttpException(
                Response.error<Any>(
                    409, ResponseBody.create(
                        MediaType.parse("plain/text"), "An unexpected error happened"
                    )
                )
            )
            !isNetAvailable -> throw IOException("An unexpected error happened")
            else -> ItemNetResponse("", "", 0.0, "")
        }
    }

    override suspend fun deleteAllItemsDb() {  // if it has an exception throw it, otherwise do nothing(it means items were deleted successfully).
        if (hasSqliteException) throw SQLiteException("unexpected error happened ")
    }

    override suspend fun insertItemsDb(items: List<ItemEntity>) {  // if it has an exception throw it, otherwise do nothing(it means items were added successfully).
        if (hasSqliteException) throw SQLiteException("unexpected error happened ")

    }

    @Throws(SQLiteException::class)
    override suspend fun getAllItemsDb(): List<ItemEntity> {
        return if (hasSqliteException) throw SQLiteException("unexpected error happened ")
        else {
            createItemsListDb()
        }
    }


    // convert ItemNetResponse to local Item model
    fun getLocalItemsList() = itemsList.map {
        it.toItem()

    }

    // create a fake api response
    private fun createItemsList(): List<ItemNetResponse> {
        itemsList.clear()
        for (i: Int in 0 until size) {
            (itemsList).add(
                ItemNetResponse(
                    text = "text ${i},",
                    image = buildImageUrl(text = "text $i"),
                    confidence = i.toDouble(),
                    id = i.toString()
                )
            )
        }
        return itemsList
    }

    // create a fake database response
    private fun createItemsListDb(): List<ItemEntity> {
        itemsListDb.clear()
        for (i: Int in 0 until size) {
            itemsListDb.add(
                ItemEntity(
                    text = "text ${i},",
                    image = buildImageUrl(text = "text $i"),
                    confidence = i.toDouble(),
                    id = i.toString()
                )
            )
        }
        return itemsListDb
    }


}