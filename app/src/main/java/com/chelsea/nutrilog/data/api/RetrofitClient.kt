package com.chelsea.nutrilog.data.api

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    // ⚠️ PENTING: Update BASE_URL sesuai setup Anda
    private const val BASE_URL = "http://10.0.2.2:8000/api/"

    // Untuk device fisik, ganti ke:
    // private const val BASE_URL = "http://192.168.1.100:8000/api/"

    fun createRetrofit(context: Context): Retrofit {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            // Add auth interceptor untuk auto-attach token
            .addInterceptor(AuthInterceptor(context))
            // Add logging untuk development
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiService(context: Context): ApiService {
        val retrofit = createRetrofit(context)
        return retrofit.create(ApiService::class.java)
    }
}
