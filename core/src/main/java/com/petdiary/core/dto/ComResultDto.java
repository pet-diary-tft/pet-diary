package com.petdiary.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.petdiary.core.exception.ResponseCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ComResultDto {
    private int httpStatusCode = ResponseCode.SUCCESS.getHttpStatusCode();
    private int code = ResponseCode.SUCCESS.getCode();
    private String message = ResponseCode.SUCCESS.getMessage();

    @JsonInclude(Include.NON_NULL)
    private Integer status = null;

    public ComResultDto() {

    }

    public ComResultDto(ResponseCode rc) {
        this.httpStatusCode = rc.getHttpStatusCode();
        this.code = rc.getCode();
        this.message = rc.getMessage();
        this.status = rc.getHttpStatusCode();
    }
}
