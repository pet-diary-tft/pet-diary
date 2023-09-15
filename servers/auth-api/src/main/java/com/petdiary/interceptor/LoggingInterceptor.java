package com.petdiary.interceptor;

import com.petdiary.core.utils.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) throws Exception {
        StringBuilder logBuilder = new StringBuilder();

        logBuilder.append("[Request Info]");
        logBuilder.append("[").append(HttpUtil.getClientIp(request)).append("]");
        logBuilder.append("[").append(request.getMethod()).append("]");
        logBuilder.append(request.getRequestURI());
        Map<String, String[]> paramMap = request.getParameterMap();
        if (!paramMap.isEmpty()) {
            String params = paramMap.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + String.join(",", entry.getValue()))
                    .collect(Collectors.joining("&"));
            logBuilder.append("?").append(params);
        }
        logBuilder.append(" (").append(HttpUtil.getUserAgent(request)).append(")");
        log.info(logBuilder.toString());
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
