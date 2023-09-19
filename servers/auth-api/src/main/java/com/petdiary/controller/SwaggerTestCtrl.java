package com.petdiary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petdiary.core.dto.ComResponseDto;
import com.petdiary.core.dto.ComResponseEntity;
import com.petdiary.core.utils.LoggingUtil;
import com.petdiary.domain.rdspetdiarymembershipdb.dto.MemberDomain;
import com.petdiary.dto.req.SwaggerTestReq;
import com.petdiary.dto.res.SwaggerTestRes;
import com.petdiary.service.SwaggerTestSvc;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/swagger-test")
@RequiredArgsConstructor
@Slf4j
public class SwaggerTestCtrl {
    private final SwaggerTestSvc swaggerTestSvc;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ComResponseEntity<SwaggerTestRes.Test1Dto> test1(
            HttpServletRequest request,
            SwaggerTestReq.Test1Dto reqDto
    ) {
        LoggingUtil<SwaggerTestReq.Test1Dto> loggingUtil = new LoggingUtil<>(objectMapper);
        loggingUtil.logDto(request, reqDto);
        return new ComResponseEntity<>(new ComResponseDto<>(swaggerTestSvc.test1(reqDto)));
    }

    @GetMapping("/member")
    public ComResponseEntity<List<MemberDomain.Dto>> test2() {
        return new ComResponseEntity<>(new ComResponseDto<>(swaggerTestSvc.getMemberList()));
    }
}
