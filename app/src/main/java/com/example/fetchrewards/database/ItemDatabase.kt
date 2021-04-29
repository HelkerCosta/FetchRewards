package com.example.fetchrewards.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fetchrewards.domain.ItemEntity

@Database(entities = [ItemEntity::class], version = 1, exportSchema = false)
abstract class ItemDatabase: RoomDatabase() {
    abstract fun getDao(): ItemDao
}