package com.petdiary.domain.redispetdiary.dto;

import lombok.*;

public class MemberRedis {
    @Getter @Setter @Builder
    @NoArgsConstructor @AllArgsConstructor
    @ToString
    public static class Dto {
        private Long idx;
        private String email;
        private String password;
        private String name;
        private Byte status;
        private String roles;
        private Integer tokenVersion;
    }

    @Getter @Setter @Builder
    @NoArgsConstructor @AllArgsConstructor
    @ToString
    public static class AccessTokenDto {
        private Long memberIdx;
        private Integer tokenVersion;
    }
}
