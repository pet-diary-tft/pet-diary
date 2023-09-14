package com.petdiary.core.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ResponseCode {
    // SYSTEM
    SYSTEM_001("com.fasterxml.jackson.core.io.JsonEOFException", 40000001, 400, "JSON 변환 중 문제가 있습니다."),
    SYSTEM_002("com.fasterxml.jackson.core.JsonParseException", 40000001, 400, "JSON 변환 중 문제가 있습니다."),
    SYSTEM_003("com.fasterxml.jackson.databind.exc.MismatchedInputException", 40000001, 400, "JSON 변환 중 문제가 있습니다."),
    SYSTEM_004("com.fasterxml.jackson.databind.JsonMappingException", 40000001, 400, "JSON 변환 중 문제가 있습니다."),
    SYSTEM_005("com.fasterxml.jackson.databind.exc.InvalidDefinitionException", 40000001, 400, "JSON 변환 중 문제가 있습니다."),
    SYSTEM_006("org.springframework.http.converter.HttpMessageConversionException", 40000001, 400, "JSON 변환 중 문제가 있습니다."),
    SYSTEM_007("org.springframework.http.converter.HttpMessageNotReadableException", 40000001, 400, "JSON 변환 중 문제가 있습니다."),
    SYSTEM_008("parameter.validation.error", 40000011, 400, "입력값 오류 입니다."),
    SYSTEM_009("org.springframework.validation.BindException", 40000011, 400, "입력값 오류 입니다."),
    SYSTEM_010("parameter.validation.IllegalArgumentException", 40000011, 400, "입력값 오류 입니다."),
    SYSTEM_011("java.lang.IllegalArgumentException", 40000011, 400, "입력값 오류 입니다."),
    SYSTEM_012("org.springframework.web.bind.MethodArgumentNotValidException", 40000011, 400, "입력값 오류 입니다."),
    SYSTEM_013("org.springframework.data.mapping.PropertyReferenceException", 40000101, 400, "올바른 키워드가 아닙니다."),
    SYSTEM_014("org.springframework.security.authentication.BadCredentialsException", 40100001, 401, "아이디 또는 비밀번호가 일치하지않습니다."),
    SYSTEM_015("org.springframework.security.authentication.DisabledException", 40100002, 401, "유효하지 않은 사용자입니다."),
    SYSTEM_016("org.springframework.security.access.AccessDeniedException", 40300001, 403, "접근이 거부되었습니다."),
    SYSTEM_017("org.springframework.web.servlet.NoHandlerFoundException", 40400001, 404, "지원하지 않는 URL"),
    SYSTEM_018("org.springframework.web.HttpRequestMethodNotSupportedException", 40500001, 405, "잘못된 요청입니다."),
    SYSTEM_019("org.springframework.web.multipart.MaxUploadSizeExceededException", 50000001, 500, "허용 파일 크기를 초과했습니다."),
    SYSTEM_020("org.springframework.jdbc", 50000011, 500, "데이터베이스 오류"),
    SYSTEM_021("org.apache.ibatis.reflection.ReflectionException", 50000011, 500, "데이터베이스 오류"),
    SYSTEM_022("org.springframework.dao.DataAccessException", 50000012, 500, "데이터베이스 오류"),
    SYSTEM_023("org.apache.ibatis.javassist.NotFoundException", 50000012, 500, "데이터베이스 오류"),
    SYSTEM_024("org.springframework.dao.DataIntegrityViolationException", 50000013, 500, "데이터베이스 오류(외래키 참조 조건)"),
    SYSTEM_025("java.sql.SQLException", 50000014, 500, "데이터 베이스 SQL 에러"),
    SYSTEM_026("org.springframework.jdbc.BadSqlGrammarException", 50000015, 500, "데이터베이스 오류"),
    SYSTEM_027("java.lang.ArithmeticException", 50000015, 500, "데이터베이스 오류"),
    SYSTEM_028("org.springframework.dao.DuplicateKeyException", 50000031, 500, "중복된 값이 존재합니다."),
    SYSTEM_029("java.security.NoSuchAlgorithmException", 50000041, 500, "해시 변환 오류"),
    // CUSTOMS
    SUCCESS("success", 20000000, 200, "정상 처리 되었습니다."),
    INVALID("invalid", 40010001, 400, "입력값 오류 입니다."),
    ALREADY_EXISTS("already_exists", 40010002, 400, "이미 존재하는 데이터 입니다."),
    NOT_EXISTS("not_exists", 40010003, 400, "존재하지않는 데이터 입니다."),
    UNAUTHORIZED("unauthorized", 40110021, 401, "로그인이 필요합니다."),
    EXPIRED_JWT("expired_jwt", 40110022, 401, "만료된 토큰입니다."),
    INVALID_JWT("invalid_jwt", 40110023, 401, "유효하지않은 토큰입니다."),
    INVALID_REFRESH_TOKEN("invalid_refresh_token", 40110024, 401, "유효하지않은 토큰입니다."),
    EXPIRED_REFRESH_TOKEN("expired_refresh_token", 40110025, 401, "만료된 토큰입니다."),
    DISABLED_ACCOUNT("disabled_account", 40110026, 401, "사용 불가능한 계정입니다."),
    FORBIDDEN("forbidden", 40310001, 403, "이 페이지를 볼 수 있는 권한이 없습니다."),
    NOT_DEFINED("not_define", 50019999, 500, "정의되지않은 오류입니다."),
    // TMP
    PASSWORD_CONFIRM("password_confirm", 40011001, 400, "비밀번호 확인 값이 일치하지 않습니다."),
    ALREADY_EXISTS_EMAIL("already_exists_email", 40011002, 400, "이미 존재하는 이메일 입니다."),
    ;

    // 키 등록
    private static final Map<String, ResponseCode> BY_KEY = new HashMap<>();
    static {
        for (ResponseCode rc: values()) {
            BY_KEY.put(rc.key, rc);
        }
    }

    private final String key;
    private final int code;
    private final int httpStatusCode;
    private final String message;

    ResponseCode(String key, int code, int httpStatusCode, String message) {
        this.key = key;
        this.code = code;
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

    public static ResponseCode findByKey(String key) {
        return BY_KEY.getOrDefault(key, NOT_DEFINED);
    }

    public static ResponseCode getSuccess() {
        return SUCCESS;
    }
}
