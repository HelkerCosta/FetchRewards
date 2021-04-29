package com.example.fetchrewards.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fetchrewards.database.ItemDao
import com.example.fetchrewards.database.ItemDatabase
import com.example.fetchrewards.network.ItemAPI
import com.example.fetchrewards.repository.Repository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideItemAPI(): ItemAPI {
        return Retrofit.Builder()
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ItemAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideRepo(
        api: ItemAPI,
        dao: ItemDao
    ): Repository{
        return Repository(api, dao) as Repository
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ItemDatabase{
        return Room.databaseBuilder(
            context,
            ItemDatabase::class.java,
            "item.db"
        ).build()
    }

    @Provides
    fun provideDao(itemDatabase: ItemDatabase): ItemDao{
        return itemDatabase.getDao()
    }
}