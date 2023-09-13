package com.petdiary.ctrl.factory;

import com.petdiary.dto.req.SwaggerTestReq;

import java.util.ArrayList;

public class SwaggerTestDtoFactory {
    private static final byte BYTE_TEST_1 = 1;
    private static final String STRING_TEST_2 = "test";
    private static final long LONG_TEST_3 = 100000;
    private static final long SUB_LONG_TEST = 2560000;
    private static final String SUB_STRING_TEST = "subTest";

    public static SwaggerTestReq.Test1Dto createTest1ReqDto() {
        SwaggerTestReq.Test1Dto reqDto = new SwaggerTestReq.Test1Dto();
        reqDto.setByteTest1(BYTE_TEST_1);
        reqDto.setStringTest2(STRING_TEST_2);
        reqDto.setLongTest3(LONG_TEST_3);
        reqDto.setTest2DtoList(new ArrayList<>());

        SwaggerTestReq.Test2Dto subReqDto = new SwaggerTestReq.Test2Dto();
        subReqDto.setSubLongTest(SUB_LONG_TEST);
        subReqDto.setSubStringTest(SUB_STRING_TEST);
        reqDto.getTest2DtoList().add(subReqDto);

        return reqDto;
    }
}
