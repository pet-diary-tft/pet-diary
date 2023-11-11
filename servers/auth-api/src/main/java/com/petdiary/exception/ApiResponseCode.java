package com.petdiary.exception;

import com.petdiary.core.exception.ResponseCodeInterface;
import lombok.Getter;

@Getter
public enum ApiResponseCode implements ResponseCodeInterface {
    PASSWORD_CONFIRM("password_confirm", 40011001, 400, "비밀번호 확인 값이 일치하지 않습니다."),
    ALREADY_EXISTS_EMAIL("already_exists_email", 40011002, 400, "이미 존재하는 이메일 입니다."),
    OLD_PASSWORD_CONFIRM("old_password_confirm", 40011003, 400, "이전 비밀번호가 일치하지 않습니다."),
    MEMBER_NOT_FOUND("member_not_found", 40011004, 400, "존재하지않는 회원 입니다."),
    IS_SOCIAL_LOGIN_MEMBER("is_social_login_member", 40011005, 400, "소셜 로그인 회원 입니다.")
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
