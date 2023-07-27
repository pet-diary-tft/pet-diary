package com.petdiary.core.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ComResponseDto<T> {
    private ComResultDto result = new ComResultDto();
    private T body;

    public ComResponseDto() {
    }

    public ComResponseDto(T body) {
        this.body = body;
    }
}
