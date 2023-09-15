package com.petdiary.dto.res;

import lombok.Builder;
import lombok.Getter;

public class AuthRes {
    @Getter @Builder
    public static class LoginDto {
        private Long idx;
        private String accessToken;
        private String refreshToken;
    }

    @Getter @Builder
    public static class AccessTokenDto {
        private String accessToken;
    }

    @Getter @Builder
    public static class SignupDto {
        private String email;
        private String name;
    }
}
