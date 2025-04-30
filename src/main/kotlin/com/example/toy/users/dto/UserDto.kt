package com.example.toy.users.dto

data class CreateUserDto(
    val name: String,
    val email: String
)

data class UpdateUserDto(
    val name: String?,
    val email: String?
)
