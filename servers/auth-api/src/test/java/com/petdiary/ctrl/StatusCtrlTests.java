package com.petdiary.ctrl;

import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.petdiary.controller.StatusCtrl;
import com.petdiary.ctrl.config.CtrlTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatusCtrl.class)
public class StatusCtrlTests extends CtrlTestConfig {
    @Test
    public void testHello() throws Exception {
        // 요청 실행
        ResultActions result = mockMvc.perform(get("/api/v1/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.httpStatusCode").value(200)) // httpStatusCode 값 검증
                .andExpect(jsonPath("$.result.code").value("2000000")) // code 값 검증
                .andExpect(jsonPath("$.result.message").value("정상 처리 되었습니다.")) // message 값 검증
                .andExpect(jsonPath("$.body").isEmpty()) // body가 null임을 검증
                .andDo(print())  // 결과를 콘솔에 출력
                .andDo(document("status-doc", ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                                .description("서버 상태 API")
                                .build()
                )));  // spring-restdocs를 사용하여 문서 생성
    }
}
