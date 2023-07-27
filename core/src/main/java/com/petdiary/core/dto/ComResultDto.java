package com.petdiary.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter @Setter @ToString
public class ComResultDto {
    private int httpStatusCode = HttpStatus.OK.value();
    private String code = "2000000";
    private String message = "정상 처리 되었습니다.";

    @JsonInclude(Include.NON_NULL)
    private String status = null;
}
