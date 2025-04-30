package com.example.toy.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JwtUtil {
    private const val SECRET = "my-secret-key-very-secure" // 실무에서는 env로 관리
    private val algorithm = Algorithm.HMAC256(SECRET)

    fun generateToken(userId: String, email: String, expiresIn: Long = 3600): String {
        return JWT.create()
            .withSubject(userId)
            .withClaim("email", email)
            .withIssuedAt(Date())
            .withExpiresAt(Date(System.currentTimeMillis() + expiresIn * 1000))
            .sign(algorithm)
    }

    fun validateToken(token: String): String? {
        return try {
            val verifier = JWT.require(algorithm).build()
            val decoded = verifier.verify(token)
            decoded.subject
        } catch (e: Exception) {
            print(e)
            null
        }
    }
}
