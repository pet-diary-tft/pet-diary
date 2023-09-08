package com.petdiary.dto.req;

import lombok.Getter;
import lombok.Setter;

public class AuthReq {
    @Getter @Setter
    public static class LoginDto {
        private String email;
        private String password;
    }

    @Getter @Setter
    public static class AccessTokenDto {
        private Long userIdx;
        private String refreshToken;
    }

    @Getter @Setter
    public static class SignupDto {
        private String email;
        private String name;
        private String password;
        private String passwordConfirm;
    }
}
