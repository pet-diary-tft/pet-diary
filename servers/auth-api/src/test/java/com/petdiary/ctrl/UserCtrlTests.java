package com.petdiary.ctrl;

import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.petdiary.controller.UserCtrl;
import com.petdiary.core.exception.ResponseCode;
import com.petdiary.ctrl.config.CtrlTestConfig;
import com.petdiary.ctrl.config.TestConstants;
import com.petdiary.service.UserSvc;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserCtrl.class)
@Import(UserSvc.class) // 실제 UserSvc를 사용
public class UserCtrlTests extends CtrlTestConfig {
    @Test
    public void testMyLoggedIn() throws Exception {
        mockMvc.perform(get("/api/v1/user/my"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.result.code").value(ResponseCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.body.idx").value(TestConstants.TEST_USER_IDX))
                .andExpect(jsonPath("$.body.email").value(TestConstants.TEST_USER_EMAIL))
                .andExpect(jsonPath("$.body.name").value(TestConstants.TEST_USER_NAME))
                .andExpect(jsonPath("$.body.loggedIn").value(true))
                .andDo(document(getDocumentName(), ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .tag("UserCtrl")
                                .description("MY")
                                .responseFields(
                                        commonResBodyFields(
                                                fieldWithPath("body.idx").description("회원 고유번호"),
                                                fieldWithPath("body.email").description("이메일"),
                                                fieldWithPath("body.name").description("닉네임"),
                                                fieldWithPath("body.loggedIn").description("로그인 상태")
                                        )
                                )
                                .build()
                )));
    }

    @Test
    public void testMyLoggedOut() throws Exception {
        // 로그아웃
        SecurityContextHolder.clearContext();

        mockMvc.perform(get("/api/v1/user/my"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.result.code").value(ResponseCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.body.loggedIn").value(false))
                .andDo(document(getDocumentName(), ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .tag("UserCtrl")
                                .description("MY")
                                .responseFields(
                                        commonResBodyFields(
                                                fieldWithPath("body.idx").description("회원 고유번호"),
                                                fieldWithPath("body.email").description("이메일"),
                                                fieldWithPath("body.name").description("닉네임"),
                                                fieldWithPath("body.loggedIn").description("로그인 상태")
                                        )
                                )
                                .build()
                )));
    }
}
