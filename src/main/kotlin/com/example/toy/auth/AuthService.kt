package com.example.toy.auth

import com.example.toy.common.exception.UnauthorizedException
import com.example.toy.users.UsersService
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val usersService: UsersService
) {
    // In-memory 저장소 (DB로 교체 가능)
    private val refreshTokenStore = mutableMapOf<Long, String>()

    fun login(email: String, password: String): Map<String, Any> {
        val user = usersService.findUserByEmail(email)
            ?: throw UnauthorizedException("User not found")

        if (user.password != password) {
            throw BadCredentialsException("Invalid credentials")
        }

        val accessToken = JwtUtil.generateToken(user.id, user.email, 3600)
        val refreshToken = JwtUtil.generateToken(user.id, user.email, 604800)

        refreshTokenStore[user.id] = refreshToken

        return mapOf(
            "access_token" to accessToken,
            "refresh_token" to refreshToken,
            "id" to user.id
        )
    }

    fun refresh(userId: Long, refreshToken: String): Map<String, Any> {
        val stored = refreshTokenStore[userId]
        if (stored == null || stored != refreshToken) {
            return mapOf("error" to "Invalid or expired refresh token")
        }

        val decoded = JwtUtil.validateToken(refreshToken)
            ?: return mapOf("error" to "Invalid token")

        val email = decoded.getClaim("email").asString()
        val newAccessToken = JwtUtil.generateToken(userId, email, 3600)

        return mapOf("access_token" to newAccessToken)
    }

    fun logout(userId: Long): Map<String, String> {
        refreshTokenStore.remove(userId)
        return mapOf("message" to "Logged out successfully")
    }
}
