package com.digidentity.codeassignment.catalog.data.database

import androidx.room.*

@Dao
interface ItemsDao {

    @Query("SELECT * FROM catalog_items")
   suspend fun getAllItems(): List<ItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ItemEntity>)

    @Query("DELETE FROM catalog_items")
    suspend fun deleteAllItems()


}