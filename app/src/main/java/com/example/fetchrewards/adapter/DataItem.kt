package com.example.fetchrewards.adapter

import com.example.fetchrewards.domain.ItemEntity

sealed class DataItem{
    abstract val id: Int
    data class Item(val item: ItemEntity): DataItem() {
        override val id: Int
            get() = item.id
    }

    data class Header(val listId : Int): DataItem() {
        override val id: Int
            get() = Int.MIN_VALUE
    }
}
