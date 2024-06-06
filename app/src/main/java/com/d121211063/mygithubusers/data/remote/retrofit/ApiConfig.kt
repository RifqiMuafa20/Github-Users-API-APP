package com.d121211063.mygithubusers.data.remote.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.d121211063.mygithubusers.BuildConfig
class ApiConfig {
    companion object {
        private const val baseUrl = "https://api.github.com/"

        fun getApiService(): ApiService {
            val client = OkHttpClient.Builder()
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}