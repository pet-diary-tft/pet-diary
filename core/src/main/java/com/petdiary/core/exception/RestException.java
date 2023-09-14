package com.petdiary.core.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RestException extends RuntimeException {
    private ResponseCode responseCode;
    private Throwable throwable = null;

    public RestException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
}
