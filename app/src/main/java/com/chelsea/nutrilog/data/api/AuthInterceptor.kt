package com.chelsea.nutrilog.data.api

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import com.chelsea.nutrilog.data.auth.authDataStore
import okhttp3.Interceptor
import okhttp3.Response
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (originalRequest.header("Authorization") != null) {
            return chain.proceed(originalRequest)
        }

        // Membaca token dari celengan authDataStore yang sama
        val token = runBlocking {
            context.authDataStore.data.map { preferences ->
                preferences[stringPreferencesKey("auth_token")]
            }.first()
        }

        val newRequest = if (!token.isNullOrEmpty()) {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }

        return chain.proceed(newRequest)
    }
}