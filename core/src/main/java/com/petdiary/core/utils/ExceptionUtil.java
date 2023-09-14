package com.petdiary.core.utils;

import com.petdiary.core.dto.ComResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
public class ExceptionUtil {
    public static String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    public static void handlerCommonLogging(ComResultDto resultDto, Exception exception) {
        // 500 에러는 error 레벨 Stack Trace 로깅
        if (resultDto.getHttpStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            log.error("[Exception] [Stack Trace : {}]", getStackTrace(exception));
        } else {
            log.debug("[Exception] [Stack Trace : {}]", getStackTrace(exception));
        }
        log.error("[Exception Response Message : {}]", resultDto);
    }
}
