package com.petdiary.controller;

import com.petdiary.clients.petdiaryauthclient.service.UserClientSvc;
import com.petdiary.core.dto.ComResponseDto;
import com.petdiary.core.dto.ComResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/status")
@RequiredArgsConstructor
@Slf4j
public class StatusCtrl {
    private final UserClientSvc userClientSvc;

    @GetMapping
    public ComResponseEntity<Void> hello(@RequestHeader(value = "Authorization", required = false) String token) {
        // web-client test
        userClientSvc.getUserData(token).subscribe(myDto -> log.debug(String.format("[WebClient Test] %s", myDto)));
        return new ComResponseEntity<>(new ComResponseDto<>());
    }

    /* WebClient Test
    @GetMapping
    public Mono<ComResponseEntity<UserClientRes.MyDto>> hello(@RequestHeader(value = "Authorization", required = false) String token) {
        return userClientSvc.getUserData(token)
                .map(myDto -> new ComResponseEntity<>(new ComResponseDto<>(myDto)))
                .defaultIfEmpty(new ComResponseEntity<>(new ComResponseDto<>()));
    }
    */
}
