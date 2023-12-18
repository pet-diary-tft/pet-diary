package com.petdiary.controller;

import com.petdiary.clients.petdiaryauthclient.service.UserClientSvc;
import com.petdiary.core.dto.ComResponseDto;
import com.petdiary.core.dto.ComResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/status")
@RequiredArgsConstructor
public class StatusCtrl {
    private final UserClientSvc userClientSvc;

    @GetMapping
    public ComResponseEntity<Void> hello() {
        // web-client test
        userClientSvc.getUserData().subscribe(data -> System.out.println(data.getEmail()));
        return new ComResponseEntity<>(new ComResponseDto<>());
    }
}
