package com.example.toy.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
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

    fun validateToken(token: String): DecodedJWT? {
        return try {
            val verifier = JWT.require(algorithm).build()
            verifier.verify(token) // ⬅ DecodedJWT 리턴
        } catch (e: Exception) {
            println("Token verification failed: ${e.message}")
            null
        }
    }
}
