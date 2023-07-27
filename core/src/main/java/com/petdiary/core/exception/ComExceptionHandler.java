package com.petdiary.core.exception;

import com.petdiary.core.dto.ComResponseDto;
import com.petdiary.core.dto.ComResponseEntity;
import com.petdiary.core.dto.ComResultDto;
import com.petdiary.core.utils.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ComExceptionHandler {
    private final ExceptionInfoConfig exceptionInfoConfig;

    /**
     * &#064;Valid  어노테이션 관련 처리
     * @return : ComResponseEntity<Void>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody ComResponseEntity<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) throws Exception {
        ComResultDto resultDto = exceptionInfoConfig.getResultDto(exception.getClass().getName());
        int statusCode = Integer.parseInt(resultDto.getStatus());
        ComResponseDto<Void> result = new ComResponseDto<>();
        result.getResult().setHttpStatusCode(statusCode);
        result.getResult().setCode(resultDto.getCode());
        result.getResult().setMessage(resultDto.getMessage());
        // 1. 500 에러는 error 레벨 Stack Trace 로깅
        if (resultDto.getHttpStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            log.error("[Exception] [Stack Trace : {}]", ExceptionUtil.getStackTrace(exception));
        } else {
            log.debug("[Exception] [Stack Trace : {}]", ExceptionUtil.getStackTrace(exception));
        }
        log.error("[Exception Response Message : {}]", resultDto);
        return new ComResponseEntity<>(result, HttpStatus.valueOf(statusCode));
    }

    /**
     * 어플리케이션에서 정의하는 비지니스 오류처리
     * @return : ComResponseEntity<Void>
     */
    @ExceptionHandler(RestException.class)
    public @ResponseBody ComResponseEntity<Void> handleBizException(RestException exception) throws Exception {
        ComResultDto resultDto;
        if (exception.getArrayReplace() != null) {
            resultDto = exceptionInfoConfig.getResultDto(exception.getYmlKey(), exception.getArrayReplace().toArray());
        } else {
            resultDto = exceptionInfoConfig.getResultDto(exception.getYmlKey());
        }

        int statusCode = Integer.parseInt(resultDto.getStatus());
        ComResponseDto<Void> result = new ComResponseDto<>();
        result.getResult().setHttpStatusCode(statusCode);
        result.getResult().setCode(resultDto.getCode());
        result.getResult().setMessage(resultDto.getMessage());
        // 1. 500 에러는 error 레벨 Stack Trace 로깅
        if (resultDto.getHttpStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            log.error("[Exception] [Stack Trace : {}]", ExceptionUtil.getStackTrace(exception));
        } else {
            log.debug("[Exception] [Stack Trace : {}]", ExceptionUtil.getStackTrace(exception));
        }
        log.error("[Exception Response Message : {}]", resultDto);
        return new ComResponseEntity<>(result, HttpStatus.valueOf(statusCode));
    }

    /**
     * Exception 클래스명 기준으로 미리 정의한 메세지 및 코드를 리턴
     * @return : ComResponseEntity<Void>
     */
    @ExceptionHandler(Exception.class)
    public @ResponseBody ComResponseEntity<Void> handleNoHandlerFoundException(Exception exception) throws Exception {
        ComResultDto resultDto = exceptionInfoConfig.getResultDto(exception.getClass().getName());
        int statusCode = Integer.parseInt(resultDto.getStatus());
        ComResponseDto<Void> result = new ComResponseDto<>();
        result.getResult().setHttpStatusCode(statusCode);
        result.getResult().setCode(resultDto.getCode());
        result.getResult().setMessage(resultDto.getMessage());
        // 1. 500 에러는 error 레벨 Stack Trace 로깅
        if (resultDto.getHttpStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            log.error("[Exception] [Stack Trace : {}]", ExceptionUtil.getStackTrace(exception));
        } else {
            log.debug("[Exception] [Stack Trace : {}]", ExceptionUtil.getStackTrace(exception));
        }
        log.error("[Exception Response Message : {}]", resultDto);
        return new ComResponseEntity<>(result, HttpStatus.valueOf(statusCode));
    }
}
