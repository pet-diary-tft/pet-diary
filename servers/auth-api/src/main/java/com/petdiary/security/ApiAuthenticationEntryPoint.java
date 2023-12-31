package com.petdiary.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petdiary.core.exception.ResponseCode;
import com.petdiary.core.utils.HttpUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        HttpUtil.setSecurityMiddlewareResponse(request, response, objectMapper, ResponseCode.UNAUTHORIZED);
    }
}
