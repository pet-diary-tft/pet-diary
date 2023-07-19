package com.petdiary.core.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

public class CookieUtil {
    /**
     * 요청에서 쿠키명으로 쿠키값을 조회
     * @return 일치하는 쿠키명이 없을 경우 null 반환
     */
    public static String getCookieValue(HttpServletRequest req, String cookieName) {
        // 클라이언트가 아무 쿠키도 가지고 있지않을 때
        if (req.getCookies() == null) return null;

        return Arrays.stream(req.getCookies())
                .filter(c -> c.getName().equals(cookieName))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }
}
