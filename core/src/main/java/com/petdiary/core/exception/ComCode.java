package com.petdiary.core.exception;

/**
 * Exception Code를 Enum으로 관리<br />
 * yaml code를 하드코딩 했을 때 생길 수 있는 문제를 보완하기 위해 임시방편으로 만듦<br />
 * yamlCode, code, status는 exception.yml 파일과 동일해야함<br />
 * desc는 참고용이라 굳이 같은 필요없음<br />
 * 모든 애플리케이션 모듈에서 동일하게 사용할 상태코드만 등록
 */
public enum ComCode implements Code {
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
    FORBIDDEN("forbidden", 40310001, 403, "이 페이지를 볼 수 있는 권한이 없습니다.");

    private final String yamlCode;
    private final int code;
    private final int status;
    private final String desc;

    ComCode(String yamlCode, int code, int status, String desc) {
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
