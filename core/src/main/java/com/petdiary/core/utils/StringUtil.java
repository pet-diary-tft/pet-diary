package com.petdiary.core.utils;

import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class StringUtil extends StringUtils {
    public static String encodeURIComponent(String s) {
        String result;
        result = URLEncoder.encode(s, StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20")
                .replaceAll("%21", "!")
                .replaceAll("%27", "'")
                .replaceAll("%28", "(")
                .replaceAll("%29", ")")
                .replaceAll("%7E", "~");
        return result;
    }

    public static String getRuleFileKeyName(URI uri) {
        String keyName;
        String temp = uri.toString().substring(uri.toString().lastIndexOf("/"));
        String replace1 = uri.toString().replaceFirst(temp, "." + temp.substring(1));
        keyName = replace1.substring(replace1.lastIndexOf("/") + 1);
        return keyName;
    }

    public static String getGUID32() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getRandomStr(int nSize) {
        StringBuilder temp = new StringBuilder();
        SecureRandom rnd = new SecureRandom();
        for (int i = 0; i < nSize; i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0 ->
                    // a-z
                        temp.append((char) ((rnd.nextInt(26)) + 97));
                case 1 ->
                    // A-Z
                        temp.append((char) ((rnd.nextInt(26)) + 65));
                case 2 ->
                    // 0-9
                        temp.append((rnd.nextInt(10)));
            }
        }
        return temp.toString();
    }

    public static String ifNullToEmpty(String obj) {
        String result = "";
        if (obj != null) {
            return obj;
        }
        return result;
    }

    public static String ifNullToEmpty(Object obj) {
        String result = "";
        if (obj != null) {
            result = String.valueOf(obj);
            if ("null".equals(result)) {
                result = "";
            }
            return result;
        }
        return result;
    }

    public static String formEncode(Map<String, Object> m) {
        StringBuilder s = new StringBuilder();
        for (String key : m.keySet()) {
            if (s.length() > 0) {
                s.append("&");
            }
            s.append(key).append("=").append(m.get(key));
        }
        return s.toString();
    }

    /**
     * DB LIKE 검색을 위해 %, _ 문자열에 escape 처리하는 함수
     */
    public static String escapeDbLikeSpecChars(String text) {
        // 1. 특수문자 목록
        List<Character> specialCharacters = Arrays.asList('%', '_');
        // 2. StringBuilder 객체 생성
        StringBuilder result = new StringBuilder();
        // 3. 입력받은 문자열을 순회하면서
        for (Character c : text.toCharArray()) {
            // 3-1. 특수문자가 있으면 \를 추가하고
            if (specialCharacters.contains(c)) {
                result.append('\\');
            }
            // 3-2. 문자를 그대로 출력
            result.append(c);
        }
        // 4. 결과 반환
        return result.toString();
    }
}
