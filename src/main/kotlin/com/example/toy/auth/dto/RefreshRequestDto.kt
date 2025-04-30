package com.example.toy.auth.dto

data class RefreshRequestDto(
    val userId: Long,
    val refreshToken: String
)