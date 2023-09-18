package com.petdiary.pureunit;

import com.petdiary.core.exception.ResponseCode;
import com.petdiary.exception.ApiResponseCode;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class ResponseCodeTests {
    @Test
    public void testNoDuplicateKeysOrCodes() {
        Map<String, String> keys = new HashMap<>();
        Map<Integer, String> codes = new HashMap<>();

        for (ResponseCode rc: ResponseCode.values()) {
            String codeStr = String.valueOf(rc.getCode());

            assertTrue("code 값은 8자리 숫자로 구성돼야 함: " + rc.getCode(), codeStr.length() == 8);
            assertFalse("중복된 key: " + rc.getKey(), keys.containsKey(rc.getKey()));
            assertFalse("중복된 code: " + rc.getCode(), codes.containsKey(rc.getCode()));

            keys.put(rc.getKey(), "completed");
            codes.put(rc.getCode(), "completed");
        }

        for (ApiResponseCode arc: ApiResponseCode.values()) {
            String codeStr = String.valueOf(arc.getCode());

            assertTrue("code 값은 8자리 숫자로 구성돼야 함: " + arc.getCode(), codeStr.length() == 8);
            assertTrue("애플리케이션 모듈의 code는 5번째 숫자가 0이 아니어야 함: " + arc.getCode(), codeStr.charAt(4) != '0');
            assertFalse("중복된 key: " + arc.getKey(), keys.containsKey(arc.getKey()));
            assertFalse("중복된 code: " + arc.getCode(), codes.containsKey(arc.getCode()));

            keys.put(arc.getKey(), "completed");
            codes.put(arc.getCode(), "completed");
        }
    }
}
