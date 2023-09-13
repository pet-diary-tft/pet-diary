package com.petdiary.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class SwaggerTestRes {
    @Getter @Builder
    public static class Test1Dto {
        private Byte byteTest;
        private String stringTest;
        private Long longTest;
        private List<Test2Dto> test2DtoList;
    }

    @Getter @Builder
    public static class Test2Dto {
        private Long subLongTest;
        private String subStringTest;
    }
}
