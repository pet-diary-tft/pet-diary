package com.petdiary.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiRestException extends RuntimeException {
    ApiResponseCode apiResponseCode;
    private Throwable throwable = null;

    public ApiRestException(ApiResponseCode apiResponseCode) {
        this.apiResponseCode = apiResponseCode;
    }
}
