package com.example.toy.users

import com.example.toy.users.model.User
import org.springframework.stereotype.Service

@Service
class UsersService {

    // 임시 Mock 데이터 (ID는 문자열, 실제로는 UUID나 DB ID)
    private val users = listOf(
        User(id = "1", name = "tester" , email = "test@example.com", password = "password")
    )

    fun findUserByEmail(email: String): User? {
        return users.find { it.email == email }
    }

    fun findOne(userId: String): User? {
        return users.find { it.id == userId }
    }
}