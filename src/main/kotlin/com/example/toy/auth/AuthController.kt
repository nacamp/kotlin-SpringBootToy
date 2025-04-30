package com.example.toy.auth

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController {
    private val logger = LoggerFactory.getLogger(AuthController::class.java)

    @PostMapping("/login")
    fun login(@RequestBody body: Map<String, String>): Map<String, String> {
        val email = body["email"]
        val password = body["password"]
        logger.info("email, pasword : {}-{}", email, password)
        // 임시 사용자 검증
        if (email == "test@example.com" && password == "password") {
            val token = JwtUtil.generateToken("1", email!!)
            logger.info("token : {}", token)
            return mapOf("access_token" to token)
        }

        return mapOf("error" to "Invalid credentials")
    }
}
