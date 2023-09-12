package com.petdiary.controller;

import com.petdiary.core.dto.ComResponseDto;
import com.petdiary.core.dto.ComResponseEntity;
import com.petdiary.dto.req.SwaggerTestReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/swagger-test")
@Slf4j
public class SwaggerTestCtrl {
    @GetMapping
    public ComResponseEntity<Void> test1(SwaggerTestReq.Test1Dto reqDto) {
        log.info(String.valueOf(reqDto.getLongTest3()));
        if (!reqDto.getTest2DtoList().isEmpty()) {
            log.info("sub: " + reqDto.getTest2DtoList().get(0).getSubLongTest().toString());
        }
        return new ComResponseEntity<>(new ComResponseDto<>());
    }
}
