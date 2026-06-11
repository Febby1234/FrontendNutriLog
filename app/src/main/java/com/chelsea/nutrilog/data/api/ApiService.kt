package com.chelsea.nutrilog.data.api

import com.chelsea.nutrilog.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // ============ AUTH ENDPOINTS ============

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @GET("auth/profile")
    suspend fun getProfile(): Response<UserProfile>

    // ============ FOOD ENDPOINTS ============

    @GET("food/all")
    suspend fun getAllFoods(): Response<List<FoodItem>>

    @GET("food/search")
    suspend fun searchFoods(@Query("query") query: String): Response<List<FoodItem>>

    @GET("food/{foodId}")
    suspend fun getFoodById(@Path("foodId") foodId: Int): Response<FoodItem>

    // ============ LOG ENDPOINTS ============

    @POST("log/add")
    suspend fun addFoodLog(@Body request: AddFoodLogRequest): Response<DailyLogResponse>

    @GET("log/today")
    suspend fun getTodayLog(): Response<DailyLogResponse>

    @GET("log/{date}")
    suspend fun getLogByDate(@Path("date") date: String): Response<DailyLogResponse>

    @DELETE("log/{itemId}")
    suspend fun deleteLogItem(@Path("itemId") itemId: Int): Response<MessageResponse>

    // ============ DASHBOARD ENDPOINTS ============

    @GET("dashboard/summary")
    suspend fun getDashboardSummary(): Response<DashboardSummary>
}