package com.randoms.ai_assistant.lib

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GeminiClient {
    private const val BASE_URL = "https://www.lgutimetable.online/";

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object GeminiAPIClient {
    val apiService: ApiService by lazy {
        GeminiClient.retrofit.create(ApiService::class.java)
    }
}
