package com.petdiary.dto.req;

import com.petdiary.core.validation.annotation.ByteSize;
import com.petdiary.core.validation.annotation.Email;
import com.petdiary.core.validation.annotation.Password;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class AuthReq {
    @Getter @Setter
    public static class LoginDto {
        @NotBlank
        private String email;
        @NotBlank
        private String password;
    }

    @Getter @Setter
    public static class AccessTokenDto {
        @NotNull
        private Long userIdx;
        @NotBlank
        private String refreshToken;
    }

    @Getter @Setter
    public static class SignupDto {
        @Email
        private String email;
        @ByteSize(max = 255)
        @NotBlank
        private String name;
        @Password
        private String password;
        @NotBlank
        private String passwordConfirm;
    }
}
