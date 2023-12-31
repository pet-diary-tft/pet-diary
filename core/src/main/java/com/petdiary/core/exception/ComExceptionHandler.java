package com.petdiary.core.exception;

import com.petdiary.core.dto.ComResponseDto;
import com.petdiary.core.dto.ComResponseEntity;
import com.petdiary.core.dto.ComResultDto;
import com.petdiary.core.utils.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ComExceptionHandler {
    /**
     * &#064;Valid  어노테이션 관련 처리
     * @return : ComResponseEntity<Map<String, Object>>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody ComResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ComResultDto resultDto = new ComResultDto(ResponseCode.findByKey(exception.getClass().getName()));
        ComResponseDto<Map<String, Object>> result = new ComResponseDto<>();
        result.setResult(resultDto);

        // 1. body에 @Valid에서 발생한 상세 오류 정보 추가
        HashMap<String, Object> body = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            body.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        result.setBody(body);

        ExceptionUtil.handlerCommonLogging(resultDto, exception);

        return new ComResponseEntity<>(result, HttpStatus.valueOf(resultDto.getHttpStatusCode()));
    }

    /**
     * 어플리케이션에서 정의하는 비지니스 오류처리
     * @return : ComResponseEntity<Void>
     */
    @ExceptionHandler(RestException.class)
    public @ResponseBody ComResponseEntity<Void> handleBizException(RestException exception) {
        ComResultDto resultDto = new ComResultDto(exception.getResponseCode());
        ComResponseDto<Void> result = new ComResponseDto<>();
        result.setResult(resultDto);

        ExceptionUtil.handlerCommonLogging(resultDto, exception);

        return new ComResponseEntity<>(result, HttpStatus.valueOf(resultDto.getHttpStatusCode()));
    }

    /**
     * Exception 클래스명 기준으로 미리 정의한 메세지 및 코드를 리턴
     * @return : ComResponseEntity<Void>
     */
    @ExceptionHandler(Exception.class)
    public @ResponseBody ComResponseEntity<Void> handleNoHandlerFoundException(Exception exception) {
        ComResultDto resultDto = new ComResultDto(ResponseCode.findByKey(exception.getClass().getName()));
        ComResponseDto<Void> result = new ComResponseDto<>();
        result.setResult(resultDto);

        ExceptionUtil.handlerCommonLogging(resultDto, exception);

        return new ComResponseEntity<>(result, HttpStatus.valueOf(resultDto.getHttpStatusCode()));
    }
}
