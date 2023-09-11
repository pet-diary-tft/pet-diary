package com.petdiary.ctrl;

import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.petdiary.controller.AuthCtrl;
import com.petdiary.ctrl.config.CtrlTestConfig;
import com.petdiary.dto.res.AuthRes;
import com.petdiary.service.AuthSvc;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
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
        AuthRes.LoginDto mockLoginDto = AuthRes.LoginDto.builder()
                .idx(1L)
                .accessToken("mockToken")
                .refreshToken("mockRefreshToken")
                .build();

        when(authSvc.login(any(), any(), any())).thenReturn(mockLoginDto);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\",\"password\":\"password\"}"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.result.code").value("2000000"))
                .andExpect(jsonPath("$.body.accessToken").value("mockToken"))
                .andExpect(jsonPath("$.body.refreshToken").value("mockRefreshToken"))
                .andDo(document("auth-login-doc", ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .description("로그인 API")
                                .build()
                )));
    }
}
