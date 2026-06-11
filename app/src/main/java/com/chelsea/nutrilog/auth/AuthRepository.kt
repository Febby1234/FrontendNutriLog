package com.chelsea.nutrilog.auth

import android.content.Context
import com.chelsea.nutrilog.auth.models.*
import com.chelsea.nutrilog.core.api.ApiService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext private val context: Context
) {
    suspend fun register(request: RegisterRequest): Result<AuthResponse> = try {
        val response = apiService.register(request)
        saveToken(response.token)
        Result.success(response)
    } catch (e: Exception) {
        Result.failure(e)
    }
    
    suspend fun login(request: LoginRequest): Result<AuthResponse> = try {
        val response = apiService.login(request)
        saveToken(response.token)
        Result.success(response)
    } catch (e: Exception) {
        Result.failure(e)
    }
    
    suspend fun getProfile(): Result<UserDTO> = try {
        Result.success(apiService.getProfile())
    } catch (e: Exception) {
        Result.failure(e)
    }
    
    suspend fun updateProfile(user: UserDTO): Result<UserDTO> = try {
        Result.success(apiService.updateProfile(user))
    } catch (e: Exception) {
        Result.failure(e)
    }
    
    fun getToken(): String? {
        return context.getSharedPreferences("auth", Context.MODE_PRIVATE)
            .getString("token", null)
    }
    
    fun saveToken(token: String) {
        context.getSharedPreferences("auth", Context.MODE_PRIVATE)
            .edit()
            .putString("token", token)
            .apply()
    }
    
    fun clearToken() {
        context.getSharedPreferences("auth", Context.MODE_PRIVATE)
            .edit()
            .remove("token")
            .apply()
    }
    
    fun isLoggedIn(): Boolean = getToken() != null
}
