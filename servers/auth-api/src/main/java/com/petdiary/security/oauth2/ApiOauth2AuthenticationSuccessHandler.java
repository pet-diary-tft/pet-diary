package com.petdiary.security.oauth2;

import com.petdiary.core.utils.HashUtil;
import com.petdiary.core.utils.HttpUtil;
import com.petdiary.core.utils.StringUtil;
import com.petdiary.domain.rdspetdiarymembershipdb.domain.MemberRefreshToken;
import com.petdiary.domain.rdspetdiarymembershipdb.repository.MemberRefreshTokenRepository;
import com.petdiary.properties.AuthJwtProperties;
import com.petdiary.properties.Oauth2ClientProperties;
import com.petdiary.security.ApiUserCachingService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiOauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final Oauth2ClientProperties oauth2ClientProperties;
    private final AuthJwtProperties authJwtProperties;
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;
    private final ApiUserCachingService apiUserCachingService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String userAgent = HttpUtil.getUserAgent(request);
        String clientIp = HttpUtil.getClientIp(request);

        Oauth2UserPrincipal oauth2UserPrincipal = (Oauth2UserPrincipal) authentication.getPrincipal();

        Date iat = new Date();
        Date exp = new Date(iat.getTime() + authJwtProperties.getExpiryInMs());
        String jwt = Jwts.builder()
                .setSubject(oauth2UserPrincipal.getIdx().toString())
                .setIssuedAt(iat)
                .setExpiration(exp)
                .signWith(Keys.hmacShaKeyFor(authJwtProperties.getSecret().getBytes(StandardCharsets.UTF_8)))
                .compact();

        // 3. refresh token 정보 갱신
        List<MemberRefreshToken> rtList = memberRefreshTokenRepository.getList(oauth2UserPrincipal.getIdx(), userAgent, clientIp);
        LocalDateTime rtIat = LocalDateTime.now();
        LocalDateTime rtExp = rtIat.plusDays(authJwtProperties.getRefreshExpiryInDays());
        MemberRefreshToken rt;
        String salt = StringUtil.getRandomStr(6);

        // 3-1. 새로운 환경에서 로그인
        if (rtList.isEmpty()) {
            try {
                rt = MemberRefreshToken.builder()
                        .memberIdx(oauth2UserPrincipal.getIdx())
                        .refreshToken(HashUtil.SHA256.encrypt(jwt + salt))
                        .issuedDate(rtIat)
                        .expiryDate(rtExp)
                        .userAgent(userAgent)
                        .clientIp(clientIp)
                        .build();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        // 3-2. 기존 환경에서 로그인
        else {
            rt = rtList.get(0);
            rt.setIssuedDate(rtIat);
            rt.setExpiryDate(rtExp);
        }
        memberRefreshTokenRepository.save(rt);

        // 3-3. Redis Caching
        apiUserCachingService.cachingMember(oauth2UserPrincipal, jwt, Long.valueOf(authJwtProperties.getExpiryInMs() / 1000));

        String redirectUri = UriComponentsBuilder.fromUriString(oauth2ClientProperties.getFrontRedirectUri())
                .queryParam("accessToken", jwt)
                .queryParam("refreshToken", rt.getRefreshToken())
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();

        response.sendRedirect(redirectUri);
    }
}
