package com.example.toy.common.dto

data class ApiResponse<T>(
    val success: Boolean = true,
    val timestamp: String = java.time.Instant.now().toString(),
    val data: T
)
