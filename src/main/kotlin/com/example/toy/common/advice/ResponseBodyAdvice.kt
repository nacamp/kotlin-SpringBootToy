package com.example.toy.common.advice

import com.example.toy.common.dto.ApiResponse
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

@ControllerAdvice
class GlobalResponseWrapper : ResponseBodyAdvice<Any> {

    override fun supports(
        returnType: MethodParameter,
        converterType: Class<out HttpMessageConverter<*>>
    ): Boolean {
        return true // 모든 컨트롤러 응답에 적용
        // 특정 패키지나, 컨트롤러 지정
        //return returnType.containingClass.packageName.startsWith("com.example.api")
        //return returnType.containingClass == SomeController::class.java
    }

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: org.springframework.http.server.ServerHttpRequest,
        response: org.springframework.http.server.ServerHttpResponse
    ): Any {
        // 이미 래핑된 응답이면 다시 감싸지 않도록 방지
        if (body is ApiResponse<*>) return body

        return ApiResponse(data = body)
    }
}
