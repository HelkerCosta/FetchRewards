package com.example.fetchrewards.util

import com.example.fetchrewards.domain.ItemEntity
import com.example.fetchrewards.network.ItemDTO

fun ArrayList<ItemDTO>.asDomain() : List<ItemEntity>{
    return map {
        ItemEntity(
            id = it.id,
            listId = it.listId,
            name = it.name.orEmpty()
        )
    }
}

fun List<ItemDTO>.asDomain() : List<ItemEntity>{
    return map {
        ItemEntity(
            id = it.id,
            listId = it.listId,
            name = it.name.orEmpty()
        )
    }
}