package com.petdiary.core.exception;

public interface ResponseCodeInterface {
    String getKey();
    int getCode();
    int getHttpStatusCode();
    String getMessage();
}
