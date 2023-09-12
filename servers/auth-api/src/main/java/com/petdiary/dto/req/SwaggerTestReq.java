package com.petdiary.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SwaggerTestReq {
    @Getter @Setter
    public static class Test1Dto {
        private Byte byteTest1;
        private String stringTest2;
        private Long longTest3;
        private List<Test2Dto> test2DtoList;
    }

    @Getter @Setter
    public static class Test2Dto {
        private Long subLongTest;
        private String subStringTest;
    }
}
