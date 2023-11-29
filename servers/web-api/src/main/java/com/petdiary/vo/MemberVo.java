package com.petdiary.vo;

import lombok.Getter;

@Getter
public class MemberVo {
    private Long idx = 1L;
    private String email = "test@example.com";
    private String name = "테스트";
    private String socialType = "none";
    private boolean loggedIn = true;
}
