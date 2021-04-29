package com.example.fetchrewards.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fetchrewards.domain.ItemEntity

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items : List<ItemEntity>)
    @Query("SELECT * FROM item_table ORDER BY listId")
    suspend fun getItems(): List<ItemEntity>
}