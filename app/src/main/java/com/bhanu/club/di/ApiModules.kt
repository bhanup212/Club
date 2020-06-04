package com.bhanu.club.di

import com.bhanu.club.network.ApiClient
import org.koin.dsl.module
import retrofit2.Retrofit


/**
 * Created by Bhanu Prakash Pasupula on 04,Jun-2020.
 * Email: pasupula1995@gmail.com
 */

val apiModule = module {

    fun provideRetrofitApi(): ApiClient = ApiClient.apiClient.create(ApiClient::class.java)

    single { provideRetrofitApi() }
}