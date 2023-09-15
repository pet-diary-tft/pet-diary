package com.petdiary.ctrl;

import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.petdiary.controller.StatusCtrl;
import com.petdiary.core.exception.ResponseCode;
import com.petdiary.ctrl.config.CtrlTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatusCtrl.class)
public class StatusCtrlTests extends CtrlTestConfig {
    @Test
    public void testHello() throws Exception {
        mockMvc.perform(get("/api/v1/status"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.result.httpStatusCode").value(ResponseCode.SUCCESS.getHttpStatusCode())) // httpStatusCode 값 검증
                .andExpect(jsonPath("$.result.code").value(ResponseCode.SUCCESS.getCode())) // code 값 검증
                .andExpect(jsonPath("$.result.message").value(ResponseCode.SUCCESS.getMessage())) // message 값 검증
                .andExpect(jsonPath("$.body").isEmpty()) // body가 null임을 검증
                .andDo(document(getDocumentName(), ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .tag("StatusCtrl")
                                .description("서버 상태 API")
                                .responseFields(commonRes())
                                .build()
                )));  // spring-restdocs를 사용하여 문서 생성
    }
}
