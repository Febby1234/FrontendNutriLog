package com.chelsea.nutrilog.data.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// 1. UBAH NAMA VARIABELNYA DI SINI AGAR TIDAK BENTROK DENGAN FILE LAIN
val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_preferences")

class TokenManager(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
    }

    // Save token dan user ID
    suspend fun saveToken(token: String, userId: String) {
        // 2. Ubah context.dataStore menjadi context.authDataStore
        context.authDataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
            preferences[USER_ID_KEY] = userId
        }
    }

    // Get token
    // 3. Ubah context.dataStore menjadi context.authDataStore
    fun getToken(): Flow<String?> = context.authDataStore.data.map { preferences ->
        preferences[TOKEN_KEY]
    }

    // Get user ID
    // 4. Ubah context.dataStore menjadi context.authDataStore
    fun getUserId(): Flow<String?> = context.authDataStore.data.map { preferences ->
        preferences[USER_ID_KEY]
    }

    // Check if logged in
    // 5. Ubah context.dataStore menjadi context.authDataStore
    fun isLoggedIn(): Flow<Boolean> = context.authDataStore.data.map { preferences ->
        preferences[TOKEN_KEY] != null
    }

    // Logout
    suspend fun logout() {
        // 6. Ubah context.dataStore menjadi context.authDataStore
        context.authDataStore.edit { preferences ->
            preferences.clear()
        }
    }
}