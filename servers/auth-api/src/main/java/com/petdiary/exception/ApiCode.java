package com.petdiary.exception;

import com.petdiary.core.exception.Code;

public enum ApiCode implements Code {
    PASSWORD_CONFIRM("password_confirm", 40011001, 400, "비밀번호 확인 값이 일치하지 않습니다."),
    ALREADY_EXISTS_EMAIL("already_exists_email", 40011002, 400, "이미 존재하는 이메일 입니다."),
    ;

    private final String yamlCode;
    private final int code;
    private final int status;
    private final String desc;

    ApiCode(String yamlCode, int code, int status, String desc) {
        this.yamlCode = yamlCode;
        this.code = code;
        this.status = status;
        this.desc = desc;
    }

    @Override
    public String getYamlCode() {
        return this.yamlCode;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public int getStatus() {
        return this.status;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
