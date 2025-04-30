package com.example.toy.users

import com.example.toy.users.dto.CreateUserDto
import com.example.toy.users.dto.UpdateUserDto
import com.example.toy.users.entity.User
import org.springframework.stereotype.Service

@Service
class UsersService(
    private val userRepository: UserRepository
) {
    fun create(dto: CreateUserDto): User {
        val user = User(
            name = dto.name,
            email = dto.email,
            password = dto.password
        )
        return userRepository.save(user)
    }

    fun findAll(): List<User> = userRepository.findAll()

    fun findOne(id: Long): User? = userRepository.findById(id).orElse(null)

    fun update(id: Long, dto: UpdateUserDto): User? {
        val existing = userRepository.findById(id).orElse(null) ?: return null
        val updated = existing.copy(
            name = dto.name ?: existing.name,
            email = dto.email ?: existing.email
        )
        return userRepository.save(updated)
    }

    fun delete(id: Long): Boolean {
        return if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            true
        } else false
    }

    fun changePassword(id: Long, newPassword: String): Boolean {
        val existing = userRepository.findById(id).orElse(null) ?: return false
        val updated = existing.copy(password = newPassword)
        userRepository.save(updated)
        return true
    }

    fun findUserByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }
}