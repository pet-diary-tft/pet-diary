package com.petdiary.dto.res;

import lombok.Builder;
import lombok.Getter;

public class UserRes {
    @Getter @Builder
    public static class MyDto {
        private Long idx;
        private String email;
        private String name;
        private String socialType;
        private boolean loggedIn;
    }
}
