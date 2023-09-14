package com.petdiary.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.petdiary.core.exception.ComCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter @Setter @ToString
public class ComResultDto {
    private int httpStatusCode = HttpStatus.OK.value();
    private String code = String.valueOf(ComCode.SUCCESS.getCode());
    private String message = ComCode.SUCCESS.getDesc();

    @JsonInclude(Include.NON_NULL)
    private String status = null;
}
