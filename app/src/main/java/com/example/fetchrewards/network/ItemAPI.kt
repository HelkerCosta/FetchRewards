package com.example.fetchrewards.network

import retrofit2.Response
import retrofit2.http.GET

interface ItemAPI {
    @GET("hiring.json")
    suspend fun getItems(): Response<ArrayList<ItemDTO>>
}
