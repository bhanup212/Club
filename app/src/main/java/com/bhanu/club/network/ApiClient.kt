package com.bhanu.club.network

import com.bhanu.club.model.Club
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit


/**
 * Created by Bhanu Prakash Pasupula on 04,Jun-2020.
 * Email: pasupula1995@gmail.com
 */
interface ApiClient {
    @GET("/api/json/get/Vk-LhK44U")
    suspend fun getClubs(): List<Club>


    companion object {

        private val gson: Gson = GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .setLenient()
            .create()

        private val okHttpAuthClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        val apiClient = Retrofit.Builder()
            .baseUrl("https://next.json-generator.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpAuthClient)
            .build()
    }
}