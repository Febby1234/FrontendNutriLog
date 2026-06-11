package com.chelsea.nutrilog.auth.models

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String,
    val age: Int,
    val weight: Float,
    val height: Float,
    val gender: String
)

data class AuthResponse(
    val token: String,
    val user: UserDTO
)

data class UserDTO(
    val userId: Int,
    val email: String,
    val name: String,
    val age: Int,
    val weight: Float,
    val height: Float,
    val gender: String,
    val role: String,
    val createdAt: String? = null
)
