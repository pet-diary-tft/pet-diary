package com.petdiary.ctrl;

import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.petdiary.controller.AuthCtrl;
import com.petdiary.core.exception.ResponseCode;
import com.petdiary.core.exception.RestException;
import com.petdiary.ctrl.config.CtrlTestConfig;
import com.petdiary.ctrl.factory.AuthDtoFactory;
import com.petdiary.dto.req.AuthReq;
import com.petdiary.dto.res.AuthRes;
import com.petdiary.exception.ApiResponseCode;
import com.petdiary.exception.ApiRestException;
import com.petdiary.service.AuthSvc;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthCtrl.class)
public class AuthCtrlTests extends CtrlTestConfig {
    @MockBean
    private AuthSvc authSvc;

    @Test
    public void testLogin() throws Exception {
        AuthReq.LoginDto reqDto = AuthDtoFactory.createLoginReqDto();
        String jsonContent = getJsonContent(reqDto);

        AuthRes.LoginDto mockLoginDto = AuthDtoFactory.createLoginResDto();

        when(authSvc.login(any(), any(), any())).thenReturn(mockLoginDto);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.result.code").value(ResponseCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.body.idx").value(mockLoginDto.getIdx()))
                .andExpect(jsonPath("$.body.accessToken").value(mockLoginDto.getAccessToken()))
                .andExpect(jsonPath("$.body.refreshToken").value(mockLoginDto.getRefreshToken()))
                .andDo(document(getDocumentName(), ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .tag("AuthCtrl")
                                .description("로그인")
                                .requestFields(
                                        fieldWithPath("email").description("이메일"),
                                        fieldWithPath("password").description("비밀번호")
                                )
                                .responseFields(
                                        commonResBodyFields(
                                                fieldWithPath("body.idx").description("회원 고유번호"),
                                                fieldWithPath("body.accessToken").description("엑세스 토큰"),
                                                fieldWithPath("body.refreshToken").description("엑세스 토큰 재발급을 위한 refresh 토큰")
                                        )
                                )
                                .build()
                )));
    }

    @Test
    public void testLoginFail() throws Exception {
        AuthReq.LoginDto reqDto = AuthDtoFactory.createLoginReqDto();
        String jsonContent = getJsonContent(reqDto);

        when(authSvc.login(any(), any(), any())).thenThrow(BadCredentialsException.class);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.result.code").value(ResponseCode.findByKey("org.springframework.security.authentication.BadCredentialsException").getCode()))
                .andDo(document(getDocumentName(), ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .tag("AuthCtrl")
                                .description("로그인")
                                .responseFields(commonRes())
                                .build()
                )));
    }

    @Test
    public void testAccessToken() throws Exception {
        AuthReq.AccessTokenDto reqDto = AuthDtoFactory.createAccessTokenReqDto();
        String jsonContent = getJsonContent(reqDto);

        AuthRes.AccessTokenDto mockResDto = AuthDtoFactory.createAccessTokenResDto();

        when(authSvc.issueAccessToken(any())).thenReturn(mockResDto);

        mockMvc.perform(post("/api/v1/auth/access-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.result.code").value(ResponseCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.body.accessToken").value(mockResDto.getAccessToken()))
                .andDo(document(getDocumentName(), ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .tag("AuthCtrl")
                                .description("Access Token 발급")
                                .requestFields(
                                        fieldWithPath("userIdx").description("회원 고유번호"),
                                        fieldWithPath("refreshToken").description("refresh 토큰 값")
                                )
                                .responseFields(
                                        commonResBodyFields(
                                                fieldWithPath("body.accessToken").description("엑세스 토큰")
                                        )
                                )
                                .build()
                )));
    }

    @Test
    public void testAccessTokenDisabledMember() throws Exception {
        AuthReq.AccessTokenDto reqDto = AuthDtoFactory.createAccessTokenReqDto();
        String jsonContent = getJsonContent(reqDto);

        when(authSvc.issueAccessToken(any())).thenThrow(new RestException(ResponseCode.DISABLED_ACCOUNT));

        mockMvc.perform(post("/api/v1/auth/access-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.result.code").value(ResponseCode.DISABLED_ACCOUNT.getCode()))
                .andDo(document(getDocumentName(), ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .tag("AuthCtrl")
                                .description("Access Token 발급")
                                .responseFields(commonRes())
                                .build()
                )));

    }

    @Test
    public void testAccessTokenInvalidRefreshToken() throws Exception {
        AuthReq.AccessTokenDto reqDto = AuthDtoFactory.createAccessTokenReqDto();
        String jsonContent = getJsonContent(reqDto);

        when(authSvc.issueAccessToken(any())).thenThrow(new RestException(ResponseCode.INVALID_REFRESH_TOKEN));

        mockMvc.perform(post("/api/v1/auth/access-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.result.code").value(ResponseCode.INVALID_REFRESH_TOKEN.getCode()))
                .andDo(document(getDocumentName(), ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .tag("AuthCtrl")
                                .description("Access Token 발급")
                                .responseFields(commonRes())
                                .build()
                )));

    }

    @Test
    public void testAccessTokenExpiredRefreshToken() throws Exception {
        AuthReq.AccessTokenDto reqDto = AuthDtoFactory.createAccessTokenReqDto();
        String jsonContent = getJsonContent(reqDto);

        when(authSvc.issueAccessToken(any())).thenThrow(new RestException(ResponseCode.EXPIRED_REFRESH_TOKEN));

        mockMvc.perform(post("/api/v1/auth/access-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.result.code").value(ResponseCode.EXPIRED_REFRESH_TOKEN.getCode()))
                .andDo(document(getDocumentName(), ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .tag("AuthCtrl")
                                .description("Access Token 발급")
                                .responseFields(commonRes())
                                .build()
                )));

    }

    @Test
    public void testAccessTokenNotExistsMember() throws Exception {
        AuthReq.AccessTokenDto reqDto = AuthDtoFactory.createAccessTokenReqDto();
        String jsonContent = getJsonContent(reqDto);

        when(authSvc.issueAccessToken(any())).thenThrow(new RestException(ResponseCode.NOT_EXISTS));

        mockMvc.perform(post("/api/v1/auth/access-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result.code").value(ResponseCode.NOT_EXISTS.getCode()))
                .andDo(document(getDocumentName(), ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .tag("AuthCtrl")
                                .description("Access Token 발급")
                                .responseFields(commonRes())
                                .build()
                )));

    }

    @Test
    public void testEmailCheck() throws Exception {
        String email = AuthDtoFactory.getTestEmail();

        doNothing().when(authSvc).emailCheck(email);

        mockMvc.perform(get("/api/v1/auth/email-check")
                        .queryParam("email", email))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.result.code").value(ResponseCode.SUCCESS.getCode()))
                .andDo(document(getDocumentName(), ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .tag("AuthCtrl")
                                .description("존재하는 이메일인지 확인")
                                .queryParameters(
                                        parameterWithName("email")
                                                .defaultValue(email)
                                                .description("확인할 이메일")
                                )
                                .responseFields(commonRes())
                                .build()
                )));
    }

    @Test
    public void testEmailCheckInvalidEmailFormat() throws Exception {
        String email = "invalid_email_format!";

        doThrow(new RestException(ResponseCode.INVALID)).when(authSvc).emailCheck(email);

        mockMvc.perform(get("/api/v1/auth/email-check")
                        .queryParam("email", email))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result.code").value(ResponseCode.INVALID.getCode()))
                .andDo(document(getDocumentName(), ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .tag("AuthCtrl")
                                .description("존재하는 이메일인지 확인")
                                .queryParameters(
                                        parameterWithName("email")
                                                .defaultValue(email)
                                                .description("확인할 이메일")
                                )
                                .responseFields(commonRes())
                                .build()
                )));
    }

    @Test
    public void testEmailCheckAlreadyExists() throws Exception {
        String email = AuthDtoFactory.getTestEmail();

        doThrow(new RestException(ResponseCode.ALREADY_EXISTS)).when(authSvc).emailCheck(email);

        mockMvc.perform(get("/api/v1/auth/email-check")
                        .queryParam("email", email))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result.code").value(ResponseCode.ALREADY_EXISTS.getCode()))
                .andDo(document(getDocumentName(), ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .tag("AuthCtrl")
                                .description("존재하는 이메일인지 확인")
                                .queryParameters(
                                        parameterWithName("email")
                                                .defaultValue(email)
                                                .description("확인할 이메일")
                                )
                                .responseFields(commonRes())
                                .build()
                )));
    }

    @Test
    public void testSignup() throws Exception {
        AuthReq.SignupDto reqDto = AuthDtoFactory.createSignupReqDto();
        String jsonContent = getJsonContent(reqDto);

        AuthRes.SignupDto mockSignupDto = AuthDtoFactory.createSignupResDto();

        when(authSvc.signup(any())).thenReturn(mockSignupDto);

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.result.code").value(ResponseCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.body.email").value(mockSignupDto.getEmail()))
                .andExpect(jsonPath("$.body.name").value(mockSignupDto.getName()))
                .andDo(document(getDocumentName(), ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .tag("AuthCtrl")
                                .description("회원가입")
                                .requestFields(
                                        fieldWithPath("email").description("이메일"),
                                        fieldWithPath("name").description("닉네임"),
                                        fieldWithPath("password").description("비밀번호"),
                                        fieldWithPath("passwordConfirm").description("비밀번호 확인")
                                )
                                .responseFields(
                                        commonResBodyFields(
                                                fieldWithPath("body.email").description("이메일"),
                                                fieldWithPath("body.name").description("닉네임")
                                        )
                                )
                                .build()
                )));
    }

    @Test
    public void testSignupInvalidFields() throws Exception {
        AuthReq.SignupDto reqDto = AuthDtoFactory.createSignupReqDto();
        reqDto.setEmail("invalid_email");
        reqDto.setPassword("invalid_password");
        reqDto.setName("Soooooooooooooooooooooooooooooooooooo" +
                "oooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "oooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "ooooooooooooooooooooooooLooooooooooooooooooooooooooooooooooooooooooooooongName");
        String jsonContent = getJsonContent(reqDto);

        AuthRes.SignupDto mockSignupDto = AuthDtoFactory.createSignupResDto();

        when(authSvc.signup(any())).thenReturn(mockSignupDto);

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result.code").value(ResponseCode.findByKey("org.springframework.web.bind.MethodArgumentNotValidException").getCode()))
                .andDo(document(getDocumentName(), ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .tag("AuthCtrl")
                                .description("회원가입")
                                .build()
                )));
    }

    @Test
    public void testSignupInvalidPasswordConfirm() throws Exception {
        AuthReq.SignupDto reqDto = AuthDtoFactory.createSignupReqDto();
        reqDto.setPassword("1q2w3e4r5t@#");
        reqDto.setPasswordConfirm("Invalid Password Confirm");
        String jsonContent = getJsonContent(reqDto);

        when(authSvc.signup(any())).thenThrow(new ApiRestException(ApiResponseCode.PASSWORD_CONFIRM));

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result.code").value(ApiResponseCode.PASSWORD_CONFIRM.getCode()))
                .andDo(document(getDocumentName(), ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .tag("AuthCtrl")
                                .description("회원가입")
                                .build()
                )));
    }

    @Test
    public void testSignupAlreadyExistsEmail() throws Exception {
        AuthReq.SignupDto reqDto = AuthDtoFactory.createSignupReqDto();
        String jsonContent = getJsonContent(reqDto);

        when(authSvc.signup(any())).thenThrow(new ApiRestException(ApiResponseCode.ALREADY_EXISTS_EMAIL));

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result.code").value(ApiResponseCode.ALREADY_EXISTS_EMAIL.getCode()))
                .andDo(document(getDocumentName(), ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .tag("AuthCtrl")
                                .description("회원가입")
                                .build()
                )));
    }
}
