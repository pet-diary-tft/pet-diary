package com.petdiary.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petdiary.core.exception.ExceptionInfoConfig;
import com.petdiary.core.utils.HttpUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MvcAccessDeniedHandler implements AccessDeniedHandler {
    private final ExceptionInfoConfig exceptionInfoConfig;
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        HttpUtil.setAccessDeniedHandlerResponse(request, response, exceptionInfoConfig, objectMapper);
    }
}
