package com.petdiary.ctrl;

import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.petdiary.controller.SwaggerTestCtrl;
import com.petdiary.ctrl.config.CtrlTestConfig;
import com.petdiary.dto.req.SwaggerTestReq;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.Collections;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(SwaggerTestCtrl.class)
public class SwaggerTestCtrlTests extends CtrlTestConfig {
    @Test
    public void testSwagger() throws Exception {
        SwaggerTestReq.Test1Dto reqDto = new SwaggerTestReq.Test1Dto();
        reqDto.setByteTest1((byte) 1);
        reqDto.setStringTest2("test");
        reqDto.setLongTest3(100L);
        SwaggerTestReq.Test2Dto test2Dto = new SwaggerTestReq.Test2Dto();
        test2Dto.setSubLongTest(200L);
        test2Dto.setSubStringTest("subTest");
        reqDto.setTest2DtoList(Collections.singletonList(test2Dto));

        mockMvc.perform(get("/api/v1/swagger-test")
                        .queryParam("byteTest1", reqDto.getByteTest1().toString())
                        .queryParam("stringTest2", reqDto.getStringTest2())
                        .queryParam("longTest3", reqDto.getLongTest3().toString())
                        .queryParam("test2DtoList[0].subLongTest", reqDto.getTest2DtoList().get(0).getSubLongTest().toString())
                        .queryParam("test2DtoList[0].subStringTest", reqDto.getTest2DtoList().get(0).getSubStringTest()))
                .andExpect(status().isOk())
                .andDo(document("swagger-test-doc",
                        ResourceDocumentation.resource(
                            ResourceSnippetParameters.builder()
                                    .description("Swagger 테스트용 API")
                                    .queryParameters(
                                            parameterWithName("byteTest1")
                                                    .type(SimpleType.NUMBER)
                                                    .defaultValue((byte) 1)
                                                    .description("byte 테스트"),
                                            parameterWithName("stringTest2")
                                                    .defaultValue("문자열 기본값")
                                                    .description("string 테스트"),
                                            parameterWithName("longTest3")
                                                    .type(SimpleType.NUMBER)
                                                    .defaultValue(1000L)
                                                    .description("long 테스트"),
                                            parameterWithName("test2DtoList[0].subLongTest")
                                                    .type(SimpleType.NUMBER)
                                                    .defaultValue(2000L)
                                                    .optional()
                                                    .description("리스트 객체 long 테스트"),
                                            parameterWithName("test2DtoList[0].subStringTest")
                                                    .defaultValue("리스트 객체 문자열 기본값")
                                                    .optional()
                                                    .description("리스트 객체 string 테스트")
                                    )
                                    .build()
                )));
    }
}
