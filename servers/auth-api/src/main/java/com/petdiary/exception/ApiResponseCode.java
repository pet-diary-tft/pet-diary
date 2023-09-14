package com.petdiary.exception;

import com.petdiary.core.exception.ResponseCodeInterface;
import lombok.Getter;

@Getter
public enum ApiResponseCode implements ResponseCodeInterface {
    PASSWORD_CONFIRM("password_confirm", 40011001, 400, "비밀번호 확인 값이 일치하지 않습니다."),
    ALREADY_EXISTS_EMAIL("already_exists_email", 40011002, 400, "이미 존재하는 이메일 입니다."),
    ;

    private final String key;
    private final int code;
    private final int httpStatusCode;
    private final String message;

    ApiResponseCode(String key, int code, int httpStatusCode, String message) {
        this.key = key;
        this.code = code;
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }
}
