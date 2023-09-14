package com.petdiary.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petdiary.core.dto.ComResponseDto;
import com.petdiary.core.dto.ComResultDto;
import com.petdiary.core.exception.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HttpUtil {
    private static final String[] CLIENT_IP_HEADER_NAME = new String[] {
            "HTTP_CLIENT_IP", "X-Forwarded-For", "X-FORWARDED-FOR", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "Proxy-Client-IP",
            "WL-Proxy-Client-IP"
    };

    /**
     * 클라이언트의 IP 정보를 추출하는 함수
     */
    public static String getClientIp(HttpServletRequest request) {
        for (String headerName: CLIENT_IP_HEADER_NAME) {
            // 1. Client IP 정보를 담은 헤더가 있는지 확인
            String ipList = request.getHeader(headerName);
            if (ipList == null) continue;

            // 2. 세미콜론을 기준으로 문자열 자르기
            for (String ip: ipList.split(",")) {
                String trimIp = ip.trim();
                // 2-1. 올바른 IP 정보인지 확인
                InetAddress inetAddress;
                try {
                    InetAddress convertedIp = InetAddress.getByName(trimIp);
                    byte[] address = convertedIp.getAddress();
                    inetAddress = InetAddress.getByAddress(address);
                } catch (UnknownHostException e) {
                    continue;
                }

                // 2-2. Private IP 정보인지 확인
                if (inetAddress.isSiteLocalAddress()) continue;

                return trimIp;
            }
        }

        return request.getRemoteAddr();
    }

    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    /**
     * Authorization 헤더값을 추출하는 함수
     * @param prefix Bearer 등
     * @return 헤더값이 없으면 null 반환
     */
    public static String getAuthorizationHeaderValue(HttpServletRequest request, @Nullable String prefix) {
        String fullValue = request.getHeader("Authorization");

        // 1. Authorization 헤더값 없음
        if (!StringUtil.hasText(fullValue)) return null;

        // 2-1. prefix 체크가 있음
        if (StringUtil.hasText(prefix)) {
            String tmp = prefix + " ";
            // 2-1-1. 해당 prefix 있음
            if (fullValue.startsWith(tmp)) {
                String value = fullValue.substring(tmp.length());
                // 2-1-2. 최종적으로 값이 있음
                if (StringUtil.hasText(value)) {
                    return value;
                } else {
                    return null;
                }
            }
            // 2-1-2. 해당 prefix 없음
            else {
                return null;
            }
        }
        // 2-2. prefix 체크가 없음
        else {
            return fullValue;
        }
    }

    /**
     * HttpServletRequest에서 ymlKey 속성값을 읽어 원하는 에러메시지를 반환할 수 있습니다.
     */
    public static void setAccessDeniedHandlerResponse(
            HttpServletRequest request,
            HttpServletResponse response,
            ObjectMapper objectMapper
    ) throws IOException {
        int statusCode = HttpStatus.FORBIDDEN.value();

        String ymlKey = "forbidden";
        String rAtr = (String) request.getAttribute("ymlKey");
        if (StringUtil.hasText(rAtr)) {
            ymlKey = rAtr;
        }

        ComResultDto resultDto = new ComResultDto(ResponseCode.findByKey(ymlKey));
        ComResponseDto<Void> result = new ComResponseDto<>();
        result.setResult(resultDto);

        response.setStatus(statusCode);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(objectMapper.writeValueAsString(result));
    }

    /**
     * HttpServletRequest에서 ymlKey 속성값을 읽어 원하는 에러메시지를 반환할 수 있습니다.
     */
    public static void setAuthenticationEntryPointResponse(
            HttpServletRequest request,
            HttpServletResponse response,
            ObjectMapper objectMapper
    ) throws IOException {
        int statusCode = HttpStatus.UNAUTHORIZED.value();

        String ymlKey = "unauthorized";
        String rAtr = (String) request.getAttribute("ymlKey");
        if (StringUtil.hasText(rAtr)) {
            ymlKey = rAtr;
        }

        ComResultDto resultDto = new ComResultDto(ResponseCode.findByKey(ymlKey));
        ComResponseDto<Void> result = new ComResponseDto<>();
        result.setResult(resultDto);

        response.setStatus(statusCode);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(objectMapper.writeValueAsString(result));
    }
}
