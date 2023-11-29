package com.petdiary.controller;

import com.petdiary.core.dto.ComResponseDto;
import com.petdiary.core.dto.ComResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/status")
public class StatusCtrl {
    @GetMapping
    public ComResponseEntity<Void> hello() {
        return new ComResponseEntity<>(new ComResponseDto<>());
    }
}
