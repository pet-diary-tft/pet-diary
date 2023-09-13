package com.petdiary.controller;

import com.petdiary.core.dto.ComResponseDto;
import com.petdiary.core.dto.ComResponseEntity;
import com.petdiary.dto.req.SwaggerTestReq;
import com.petdiary.dto.res.SwaggerTestRes;
import com.petdiary.service.SwaggerTestSvc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/swagger-test")
@RequiredArgsConstructor
@Slf4j
public class SwaggerTestCtrl {
    private final SwaggerTestSvc swaggerTestSvc;

    @GetMapping
    public ComResponseEntity<SwaggerTestRes.Test1Dto> test1(SwaggerTestReq.Test1Dto reqDto) {
        return new ComResponseEntity<>(new ComResponseDto<>(swaggerTestSvc.test1(reqDto)));
    }
}
