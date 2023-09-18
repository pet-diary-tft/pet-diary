package com.petdiary.controller;

import com.petdiary.core.dto.ComResponseDto;
import com.petdiary.core.dto.ComResponseEntity;
import com.petdiary.core.dto.ComResultDto;
import com.petdiary.core.exception.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/status")
public class StatusCtrl {
    @GetMapping
    public ComResponseEntity<Void> hello() {
        return new ComResponseEntity<>(new ComResponseDto<>());
    }

    @GetMapping("/{errorCode}")
    public ComResponseEntity<Void> errorDocs(@PathVariable Integer errorCode) {
        ResponseCode responseCode = ResponseCode.findByCode(errorCode);
        ComResultDto comResultDto = new ComResultDto(responseCode);
        ComResponseDto<Void> comResponseDto = new ComResponseDto<>();
        comResponseDto.setResult(comResultDto);
        return new ComResponseEntity<>(comResponseDto, HttpStatus.valueOf(responseCode.getHttpStatusCode()));
    }
}
