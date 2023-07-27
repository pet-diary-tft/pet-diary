package com.petdiary.core.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ComResponseEntity<T> extends ResponseEntity<ComResponseDto<T>> {
    public ComResponseEntity() {
        super(new ComResponseDto<T>(), HttpStatus.OK);
    }

    public ComResponseEntity(HttpStatus status) {
        super(status);
    }

    public ComResponseEntity(ComResponseDto<T> body) {
        super(body, HttpStatus.OK);
    }

    public ComResponseEntity(ComResponseDto<T> body, HttpStatus status) {
        super(body, status);
        body.getResult().setHttpStatusCode(status.value());
    }
}
