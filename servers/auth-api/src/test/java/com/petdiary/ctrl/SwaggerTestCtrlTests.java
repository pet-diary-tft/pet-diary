package com.petdiary.ctrl;

import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.petdiary.controller.SwaggerTestCtrl;
import com.petdiary.core.exception.ResponseCode;
import com.petdiary.ctrl.config.CtrlTestConfig;
import com.petdiary.ctrl.factory.SwaggerTestDtoFactory;
import com.petdiary.dto.req.SwaggerTestReq;
import com.petdiary.dto.res.SwaggerTestRes;
import com.petdiary.service.SwaggerTestSvc;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(SwaggerTestCtrl.class)
public class SwaggerTestCtrlTests extends CtrlTestConfig {
    @MockBean
    private SwaggerTestSvc swaggerTestSvc;

    @Test
    public void testSwagger() throws Exception {
        SwaggerTestReq.Test1Dto reqDto = SwaggerTestDtoFactory.createTest1ReqDto();

        SwaggerTestRes.Test1Dto mockResDto = SwaggerTestDtoFactory.createTest1ResDto();

        when(swaggerTestSvc.test1(any())).thenReturn(mockResDto);

        mockMvc.perform(get("/api/v1/swagger-test")
                        .queryParam("byteTest1", reqDto.getByteTest1().toString())
                        .queryParam("stringTest2", reqDto.getStringTest2())
                        .queryParam("longTest3", reqDto.getLongTest3().toString())
                        .queryParam("test2DtoList[0].subLongTest", reqDto.getTest2DtoList().get(0).getSubLongTest().toString())
                        .queryParam("test2DtoList[0].subStringTest", reqDto.getTest2DtoList().get(0).getSubStringTest()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.result.httpStatusCode").value(ResponseCode.SUCCESS.getHttpStatusCode())) // httpStatusCode 값 검증
                .andExpect(jsonPath("$.result.code").value(ResponseCode.SUCCESS.getCode())) // code 값 검증
                .andDo(document("swagger-test-doc",
                        ResourceDocumentation.resource(
                            ResourceSnippetParameters.builder()
                                    .tag("SwaggerTestCtrl")
                                    .description("Swagger 테스트용 API")
                                    .queryParameters(
                                            parameterWithName("byteTest1")
                                                    .type(SimpleType.NUMBER)
                                                    .defaultValue(reqDto.getByteTest1())
                                                    .description("byte 테스트"),
                                            parameterWithName("stringTest2")
                                                    .defaultValue(reqDto.getStringTest2())
                                                    .description("string 테스트"),
                                            parameterWithName("longTest3")
                                                    .type(SimpleType.NUMBER)
                                                    .defaultValue(reqDto.getLongTest3())
                                                    .description("long 테스트"),
                                            parameterWithName("test2DtoList[0].subLongTest")
                                                    .type(SimpleType.NUMBER)
                                                    .defaultValue(reqDto.getTest2DtoList().get(0).getSubLongTest())
                                                    .optional()
                                                    .description("리스트 객체 long 테스트"),
                                            parameterWithName("test2DtoList[0].subStringTest")
                                                    .defaultValue(reqDto.getTest2DtoList().get(0).getSubStringTest())
                                                    .optional()
                                                    .description("리스트 객체 string 테스트")
                                    )
                                    .responseFields(
                                            commonResBodyFields(
                                                    fieldWithPath("body.byteTest").description("바이트 타입 테스트"),
                                                    fieldWithPath("body.stringTest").description("스트링 타입 테스트"),
                                                    fieldWithPath("body.longTest").description("숫자 타입 테스트"),
                                                    fieldWithPath("body.test2DtoList").description("객체 리스트 타입 테스트"),
                                                    fieldWithPath("body.test2DtoList[0].subLongTest").description("리스트 내 객체의 숫자 타입 테스트"),
                                                    fieldWithPath("body.test2DtoList[0].subStringTest").description("리스트 내 객체의 문자열 타입 테스트")
                                            )
                                    )
                                    .build()
                )))
        ;
    }
}
