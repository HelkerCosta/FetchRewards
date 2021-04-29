package com.example.fetchrewards.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
data class ItemEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val listId: Int,
    val name: String
)