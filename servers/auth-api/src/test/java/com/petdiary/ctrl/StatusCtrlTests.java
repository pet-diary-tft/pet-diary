package com.petdiary.ctrl;

import com.petdiary.controller.StatusCtrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatusCtrl.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class StatusCtrlTests {
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

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
                .andDo(document("status-hello"));  // spring-restdocs를 사용하여 문서 생성
    }
}
