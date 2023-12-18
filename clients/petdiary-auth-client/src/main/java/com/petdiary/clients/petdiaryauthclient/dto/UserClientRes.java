package com.petdiary.clients.petdiaryauthclient.dto;

import lombok.Builder;
import lombok.Getter;

public class UserClientRes {
    @Getter @Builder
    public static class MyDto {
        private Long idx;
        private String email;
        private String name;
        private String socialType;
        private boolean loggedIn;
    }
}
