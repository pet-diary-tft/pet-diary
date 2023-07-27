package com.petdiary.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petdiary.core.dto.ComResponseDto;
import com.petdiary.core.dto.ComResultDto;
import com.petdiary.core.exception.ExceptionInfoConfig;
import com.petdiary.core.utils.StringUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MvcAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ExceptionInfoConfig exceptionInfoConfig;
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        int statusCode = HttpStatus.UNAUTHORIZED.value();

        String ymlKey = "unauthorized";
        String rAtr = (String) request.getAttribute("ymlKey");
        if (StringUtil.hasText(rAtr)) {
            ymlKey = rAtr;
        }

        ComResultDto resultDto = exceptionInfoConfig.getResultDto(ymlKey);
        ComResponseDto<Void> result = new ComResponseDto<>();
        result.getResult().setHttpStatusCode(statusCode);
        result.getResult().setCode(resultDto.getCode());
        result.getResult().setMessage(resultDto.getMessage());

        response.setStatus(statusCode);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(objectMapper.writeValueAsString(result));
    }
}
