package com.petdiary.ctrl;

import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.petdiary.controller.AuthCtrl;
import com.petdiary.ctrl.config.CtrlTestConfig;
import com.petdiary.ctrl.factory.AuthDtoFactory;
import com.petdiary.dto.req.AuthReq;
import com.petdiary.dto.res.AuthRes;
import com.petdiary.service.AuthSvc;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

@AutoConfigureMockMvc(addFilters = false)
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
                .andExpect(jsonPath("$.result.code").value("2000000"))
                .andExpect(jsonPath("$.body.accessToken").value(mockLoginDto.getAccessToken()))
                .andExpect(jsonPath("$.body.refreshToken").value(mockLoginDto.getRefreshToken()))
                .andDo(document("auth-login-doc", ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .tag("AuthCtrl")
                                .description("로그인")
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
                .andExpect(jsonPath("$.result.code").value("2000000"))
                .andExpect(jsonPath("$.body.accessToken").value(mockResDto.getAccessToken()))
                .andDo(document("auth-access-token-doc", ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .tag("AuthCtrl")
                                .description("Access Token 발급")
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
                .andExpect(jsonPath("$.result.code").value("2000000"))
                .andDo(document("auth-email-check-doc", ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .tag("AuthCtrl")
                                .description("존재하는 이메일인지 확인")
                                .queryParameters(
                                        parameterWithName("email")
                                                .defaultValue(email)
                                                .description("확인할 이메일")
                                )
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
                .andExpect(jsonPath("$.result.code").value("2000000"))
                .andExpect(jsonPath("$.body.email").value(mockSignupDto.getEmail()))
                .andExpect(jsonPath("$.body.name").value(mockSignupDto.getName()))
                .andDo(document("auth-signup-doc", ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .tag("AuthCtrl")
                                .description("회원가입")
                                .build()
                )));
    }
}
