package com.petdiary.controller;

import com.petdiary.core.dto.ComResponseDto;
import com.petdiary.core.dto.ComResponseEntity;
import com.petdiary.dto.res.UserRes;
import com.petdiary.security.ApiUserPrincipal;
import com.petdiary.service.UserSvc;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserCtrl {
    private final UserSvc userSvc;

    @GetMapping("/my")
    public ComResponseEntity<UserRes.MyDto> my(@AuthenticationPrincipal ApiUserPrincipal principal) {
        return new ComResponseEntity<>(new ComResponseDto<>(userSvc.my(principal)));
    }
}
