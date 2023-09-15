package com.petdiary.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class LoggingUtil<T> {
    private final ObjectMapper objectMapper;

    /**
     * DTO를 로깅하는 함수
     */
    public void logDto(T dto) {
        try {
            log.info(String.format("[%s] %s", dto.getClass().getName(), objectMapper.writeValueAsString(dto)));
        } catch (JsonProcessingException exception) {
            log.warn(String.format("[%s] 직렬화에 실패했습니다.", dto.getClass().getName()));
        }
    }

    /**
     * 클라이언트 IP와 DTO를 로깅하는 함수
     */
    public void logDto(HttpServletRequest request, T dto) {
        try {
            log.info(String.format("[%s][%s] %s", HttpUtil.getClientIp(request), dto.getClass().getName(), objectMapper.writeValueAsString(dto)));
        } catch (JsonProcessingException exception) {
            log.warn(String.format("[%s][%s] 직렬화에 실패했습니다.", HttpUtil.getClientIp(request), dto.getClass().getName()));
        }
    }
}
