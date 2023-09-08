package com.petdiary.dto.res;

import com.petdiary.security.ApiUserPrincipal;
import lombok.Builder;
import lombok.Getter;

public class AuthRes {
    @Getter
    public static class MyDto {
        private Long idx = 0L;
        private String email = "";
        private String name = "";
        private boolean loggedIn = false;

        public MyDto(ApiUserPrincipal principal) {
            if (principal == null) return;

            this.idx = principal.getIdx();
            this.email = principal.getEmail();
            this.name = principal.getName();
            this.loggedIn = true;
        }
    }

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
