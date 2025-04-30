package com.example.toy.common.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import com.example.toy.common.dto.ErrorResponse

@RestControllerAdvice
class GlobalExceptionHandler {

    // 커스텀 예외
    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorized(e: UnauthorizedException): ResponseEntity<Map<String, Any>> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(mapOf("error" to (e.message ?: "Unauthorized")))
    }

    // 스프링에서 제공하는 기본 예외
    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentials(e: BadCredentialsException): ResponseEntity<Map<String, Any>> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(mapOf("error" to (e.message ?: "Unauthorized")))
    }

    // 전체 예외
    @ExceptionHandler(Exception::class)
    fun handleAll(e: Exception): ResponseEntity<Map<String, Any>> {
        val status = when (e) {
            is IllegalArgumentException -> HttpStatus.BAD_REQUEST
            is NoSuchElementException -> HttpStatus.NOT_FOUND
            is org.springframework.security.authentication.BadCredentialsException -> HttpStatus.UNAUTHORIZED
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
        return ResponseEntity.status(status).body(mapOf("error" to (e.message ?: "???")))
    }

//    // 모든에러를 동일한 포맷으로 리턴하고 싶으면
//    // handleUnauthorized 여기도 val errorResponse = ErrorResponse( ... 를 해주면 된다.
//    @ExceptionHandler(Exception::class)
//    fun handleAll(e: Exception): ResponseEntity<ErrorResponse> {
//        val status = when (e) {
//            is IllegalArgumentException -> HttpStatus.BAD_REQUEST
//            is NoSuchElementException -> HttpStatus.NOT_FOUND
//            is org.springframework.security.authentication.BadCredentialsException -> HttpStatus.UNAUTHORIZED
//            else -> HttpStatus.INTERNAL_SERVER_ERROR
//        }
//
//        val errorResponse = ErrorResponse(
//            statusCode = status.value(),
//            message = e.message ?: "Unexpected error"
//        )
//
//        return ResponseEntity.status(status).body(errorResponse)
//    }
}
