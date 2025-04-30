package com.example.toy.auth

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    private val logger = LoggerFactory.getLogger(AuthController::class.java)
    @PostMapping("/login")
    fun login(@RequestBody body: Map<String, String>): Map<String, Any> {
        return authService.login(body["email"] ?: "", body["password"] ?: "")
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody body: Map<String, String>): Map<String, Any> {
        val userId = body["userId"] ?: return mapOf("error" to "Missing userId")
        val refreshToken = body["refreshToken"] ?: return mapOf("error" to "Missing refreshToken")
        return authService.refresh(userId, refreshToken)
    }

    @PostMapping("/logout")
    fun logout(@RequestBody body: Map<String, String>): Map<String, String> {
        val userId = body["userId"] ?: return mapOf("error" to "Missing userId")
        return authService.logout(userId)
    }
}
