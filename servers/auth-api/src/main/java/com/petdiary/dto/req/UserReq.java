package com.petdiary.dto.req;

import com.petdiary.core.validation.annotation.Password;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class UserReq {
    @Getter @Setter
    public static class ChangePasswordDto {
        @NotBlank
        private String oldPassword;
        @Password
        private String newPassword;
        @NotBlank
        private String newPasswordConfirm;
    }
}
