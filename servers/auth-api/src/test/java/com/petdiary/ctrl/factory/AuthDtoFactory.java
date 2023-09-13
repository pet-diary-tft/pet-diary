package com.petdiary.ctrl.factory;

import com.petdiary.dto.req.AuthReq;
import com.petdiary.dto.res.AuthRes;

public class AuthDtoFactory {
    private static final String EMAIL = "test@example.com";
    private static final String PASSWORD = "1q2w3e4r5t@#";
    private static final String ACCESS_TOKEN = "mockAccessToken";
    private static final String REFRESH_TOKEN = "mockRefreshToken";
    private static final Long USER_IDX = 1L;
    private static final String NAME = "테스터";

    public static AuthReq.LoginDto createLoginReqDto() {
        AuthReq.LoginDto reqDto = new AuthReq.LoginDto();
        reqDto.setEmail(EMAIL);
        reqDto.setPassword(PASSWORD);
        return reqDto;
    }

    public static AuthRes.LoginDto createLoginResDto() {
        return AuthRes.LoginDto.builder()
                .accessToken(ACCESS_TOKEN)
                .refreshToken(REFRESH_TOKEN)
                .build();
    }

    public static AuthReq.SignupDto createSignupReqDto() {
        AuthReq.SignupDto reqDto = new AuthReq.SignupDto();
        reqDto.setEmail(EMAIL);
        reqDto.setName(NAME);
        reqDto.setPassword(PASSWORD);
        reqDto.setPasswordConfirm(PASSWORD);
        return reqDto;
    }

    public static AuthRes.SignupDto createSignupResDto() {
        return AuthRes.SignupDto.builder()
                .email(EMAIL)
                .name(NAME)
                .build();
    }

    public static AuthReq.AccessTokenDto createAccessTokenReqDto() {
        AuthReq.AccessTokenDto reqDto = new AuthReq.AccessTokenDto();
        reqDto.setUserIdx(USER_IDX);
        reqDto.setRefreshToken(REFRESH_TOKEN);
        return reqDto;
    }

    public static AuthRes.AccessTokenDto createAccessTokenResDto() {
        return AuthRes.AccessTokenDto.builder()
                .accessToken(ACCESS_TOKEN)
                .build();
    }

    public static String getTestEmail() {
        return EMAIL;
    }
}
