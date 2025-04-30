package com.example.toy.common.dto

data class ErrorResponse(
    val success: Boolean = false,
    val statusCode: Int,
    val message: String
)