package com.example.toy.auth

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.authority.SimpleGrantedAuthority


@Component
class JwtAuthFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader("Authorization")?.removePrefix("Bearer ") ?: return filterChain.doFilter(
            request,
            response
        )
        val decoded = JwtUtil.validateToken(token)
        if (decoded == null) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("Invalid or missing token")
            return
        }
        val userId = decoded.subject
        if (userId == null) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("Invalid or missing token")
            return
        }

        val auth = UsernamePasswordAuthenticationToken(
            userId, // principal
            null,   // credentials
            listOf(SimpleGrantedAuthority("ROLE_USER")) // 권한 부여 (선택적)
        )
        SecurityContextHolder.getContext().authentication = auth
        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.requestURI
        val method = request.method
        return (method == "POST" && path == "/users") || path.startsWith("/auth")
    }
}
