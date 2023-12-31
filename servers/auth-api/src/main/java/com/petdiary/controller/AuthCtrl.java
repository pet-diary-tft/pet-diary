package com.petdiary.controller;

import com.petdiary.core.dto.ComResponseDto;
import com.petdiary.core.dto.ComResponseEntity;
import com.petdiary.core.utils.HttpUtil;
import com.petdiary.dto.req.AuthReq;
import com.petdiary.dto.res.AuthRes;
import com.petdiary.service.AuthSvc;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthCtrl {
    private final AuthSvc authSvc;

    @PostMapping("/login")
    public ComResponseEntity<AuthRes.LoginDto> login(HttpServletRequest request, @Valid @RequestBody AuthReq.LoginDto reqDto) throws NoSuchAlgorithmException {
        String userAgent = HttpUtil.getUserAgent(request);
        String clientIp = HttpUtil.getClientIp(request);
        return new ComResponseEntity<>(new ComResponseDto<>(authSvc.login(reqDto, userAgent, clientIp)));
    }

    @PostMapping("/access-token")
    public ComResponseEntity<AuthRes.AccessTokenDto> accessToken(@Valid @RequestBody AuthReq.AccessTokenDto reqDto) {
        return new ComResponseEntity<>(new ComResponseDto<>(authSvc.issueAccessToken(reqDto)));
    }

    @GetMapping("/email-check")
    public ComResponseEntity<Void> emailCheck(@RequestParam(value = "email") String email) {
        authSvc.emailCheck(email);
        return new ComResponseEntity<>(new ComResponseDto<>());
    }

    @PostMapping("/signup")
    public ComResponseEntity<AuthRes.SignupDto> signup(@Valid @RequestBody AuthReq.SignupDto reqDto) {
        return new ComResponseEntity<>(new ComResponseDto<>(authSvc.signup(reqDto)));
    }
}
