package com.randoms.ai_assistant.lib

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object GeminiClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // Customize the connect timeout
            .readTimeout(30, TimeUnit.SECONDS)    // Customize the read timeout
            .writeTimeout(30, TimeUnit.SECONDS)   // Customize the write timeout
            .build()
    }
}

object GeminiAPIClient {
    val apiService: ApiService by lazy {
        GeminiClient.retrofit.create(ApiService::class.java)
    }
}
