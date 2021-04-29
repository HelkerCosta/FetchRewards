package com.example.fetchrewards.repository

import com.example.fetchrewards.database.ItemDao
import com.example.fetchrewards.domain.ItemEntity
import com.example.fetchrewards.network.ItemAPI
import com.example.fetchrewards.util.Resources
import com.example.fetchrewards.util.asDomain
import java.io.IOException
import javax.inject.Inject

class Repository @Inject constructor(
    private val itemAPI: ItemAPI,
    private val itemDao: ItemDao
) {
    suspend fun getItem(): Resources<List<ItemEntity>>{
        if(itemDao.getItems().isNullOrEmpty()){
            return try {
                val response = itemAPI.getItems()
                if(response.isSuccessful){
                    val filterResponse = response.body()?.filterNot { it.name.isNullOrEmpty()}
                    itemDao.insertItems(filterResponse!!.asDomain())
                    Resources.Success(filterResponse.asDomain())
                }else{
                    Resources.Error(message = response.errorBody().toString())
                }
            }catch (e: IOException){
                return Resources.NoInternetConnection(message = e.message)
            }
        }else{
            return Resources.Success(itemDao.getItems())
        }
    }
}