package com.petdiary.domain.rdspetdiarymembershipdb.enums;

import lombok.Getter;

@Getter
public enum MemberRoleType {
    USER("일반 회원"),
    ;

    public static class Code {
        private static final String USER = "USER";
    }

    private final String desc;

    MemberRoleType(String desc) {
        this.desc = desc;
    }
}
