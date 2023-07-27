package com.petdiary.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petdiary.core.dto.ComResponseDto;
import com.petdiary.core.dto.ComResultDto;
import com.petdiary.core.exception.ExceptionInfoConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        int statusCode = HttpStatus.FORBIDDEN.value();

        ComResultDto resultDto = exceptionInfoConfig.getResultDto("forbidden");
        ComResponseDto<Void> result = new ComResponseDto<>();
        result.getResult().setHttpStatusCode(statusCode);
        result.getResult().setCode(resultDto.getCode());
        result.getResult().setMessage(resultDto.getMessage());

        response.setStatus(statusCode);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(objectMapper.writeValueAsString(result));
    }
}
