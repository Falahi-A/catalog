package com.digidentity.codeassignment.catalog.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ItemEntity::class], version = 1)
abstract class ItemsDataBase : RoomDatabase() {
    abstract fun getDao(): ItemsDao
}