package com.petdiary.core.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.List;

@Getter @Setter
public class RestException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private String ymlKey;
    private List<String> arrayReplace;
    private Throwable throwable = null;

    public RestException(Throwable throwable) {
        this.throwable = throwable;
    }

    public RestException(String ymlKey) {
        this.ymlKey = ymlKey;
    }

    public RestException(Throwable throwable, String ymlKey) {
        this.throwable = throwable;
        this.ymlKey = ymlKey;
    }

    public RestException(String ymlKey, List<String> arrayReplace) {
        this.ymlKey = ymlKey;
        this.arrayReplace = arrayReplace;
    }

    public RestException(Throwable throwable, String ymlKey, List<String> arrayReplace) {
        this.throwable = throwable;
        this.ymlKey = ymlKey;
        this.arrayReplace = arrayReplace;
    }
}
