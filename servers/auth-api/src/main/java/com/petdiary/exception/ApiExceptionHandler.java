package com.petdiary.exception;

import com.petdiary.core.dto.ComResponseDto;
import com.petdiary.core.dto.ComResponseEntity;
import com.petdiary.core.dto.ComResultDto;
import com.petdiary.core.utils.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {
    /**
     * 어플리케이션에서 정의하는 비지니스 오류처리
     * @return : ComResponseEntity<Void>
     */
    @ExceptionHandler(ApiRestException.class)
    public @ResponseBody ComResponseEntity<Void> handleBizException(ApiRestException exception) {
        ComResultDto resultDto = new ComResultDto();
        ApiResponseCode arc = exception.getApiResponseCode();
        resultDto.setHttpStatusCode(arc.getHttpStatusCode());
        resultDto.setCode(arc.getCode());
        resultDto.setMessage(arc.getMessage());

        ComResponseDto<Void> result = new ComResponseDto<>();
        result.setResult(resultDto);

        ExceptionUtil.handlerCommonLogging(resultDto, exception);

        return new ComResponseEntity<>(result, HttpStatus.valueOf(resultDto.getHttpStatusCode()));
    }
}
