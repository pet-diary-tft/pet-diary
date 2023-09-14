package com.petdiary.service;

import com.petdiary.core.exception.ResponseCode;
import com.petdiary.core.exception.RestException;
import com.petdiary.core.utils.HashUtil;
import com.petdiary.core.utils.StringUtil;
import com.petdiary.core.utils.ValidationUtil;
import com.petdiary.domain.rdspetdiarymembershipdb.domain.Member;
import com.petdiary.domain.rdspetdiarymembershipdb.domain.MemberRefreshToken;
import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberRoleType;
import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberStatusType;
import com.petdiary.domain.rdspetdiarymembershipdb.repository.MemberRefreshTokenRepository;
import com.petdiary.domain.rdspetdiarymembershipdb.repository.MemberRepository;
import com.petdiary.dto.req.AuthReq;
import com.petdiary.dto.res.AuthRes;
import com.petdiary.properties.AuthJwtProperties;
import com.petdiary.security.ApiUserPrincipal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthSvc {
    private final MemberRepository memberRepository;
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthJwtProperties authJwtProperties;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthRes.LoginDto login(AuthReq.LoginDto reqDto, String userAgent, String clientIp) throws NoSuchAlgorithmException {
        // 1. 이메일, 비밀번호 검증
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(reqDto.getEmail(), reqDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ApiUserPrincipal principal = (ApiUserPrincipal) authentication.getPrincipal();
        Long memberIdx = principal.getIdx();

        if (!principal.isEnabled()) throw new RestException(ResponseCode.DISABLED_ACCOUNT);

        // 2. jwt 생성
        Date iat = new Date();
        Date exp = new Date(iat.getTime() + authJwtProperties.getExpiryInMs());
        String jwt = Jwts.builder()
                .setSubject(memberIdx.toString())
                .setIssuedAt(iat)
                .setExpiration(exp)
                .signWith(Keys.hmacShaKeyFor(authJwtProperties.getSecret().getBytes(StandardCharsets.UTF_8)))
                .compact();

        // 3. refresh token 정보 갱신
        List<MemberRefreshToken> rtList = memberRefreshTokenRepository.getList(memberIdx, userAgent, clientIp);
        LocalDateTime rtIat = LocalDateTime.now();
        LocalDateTime rtExp = rtIat.plusDays(authJwtProperties.getRefreshExpiryInDays());
        MemberRefreshToken rt;
        String salt = StringUtil.getRandomStr(6);

        // 3-1. 새로운 환경에서 로그인
        if (rtList.isEmpty()) {
            rt = MemberRefreshToken.builder()
                    .memberIdx(memberIdx)
                    .refreshToken(HashUtil.SHA256.encrypt(jwt + salt))
                    .issuedDate(rtIat)
                    .expiryDate(rtExp)
                    .userAgent(userAgent)
                    .clientIp(clientIp)
                    .build();
        }
        // 3-2. 기존 환경에서 로그인
        else {
            rt = rtList.get(0);
            rt.setIssuedDate(rtIat);
            rt.setExpiryDate(rtExp);
        }
        memberRefreshTokenRepository.save(rt);

        // 4. resDto
        return AuthRes.LoginDto.builder()
                .idx(memberIdx)
                .accessToken(jwt)
                .refreshToken(rt.getRefreshToken())
                .build();
    }

    @Transactional
    public AuthRes.AccessTokenDto issueAccessToken(AuthReq.AccessTokenDto reqDto) {
        // 1. 존재하는 회원인지 검증
        Member member = memberRepository.findById(reqDto.getUserIdx()).orElseThrow(() -> new RestException(ResponseCode.NOT_EXISTS));
        ApiUserPrincipal principal = ApiUserPrincipal.create(member);
        Long memberIdx = principal.getIdx();

        // 2. 사용 가능한 계정인지 검증
        if (!principal.isEnabled()) throw new RestException(ResponseCode.DISABLED_ACCOUNT);

        // 3. 리프레쉬 토큰 검증
        MemberRefreshToken rt = memberRefreshTokenRepository.findByMemberIdxAndRefreshToken(memberIdx, reqDto.getRefreshToken());
        if (rt == null) throw new RestException(ResponseCode.INVALID_REFRESH_TOKEN);
        if (rt.getExpiryDate().isBefore(LocalDateTime.now())) throw new RestException(ResponseCode.EXPIRED_REFRESH_TOKEN);

        // 4. jwt 생성
        Date iat = new Date();
        Date exp = new Date(iat.getTime() + authJwtProperties.getExpiryInMs());
        String jwt = Jwts.builder()
                .setSubject(memberIdx.toString())
                .setIssuedAt(iat)
                .setExpiration(exp)
                .signWith(Keys.hmacShaKeyFor(authJwtProperties.getSecret().getBytes(StandardCharsets.UTF_8)))
                .compact();

        // 5. 마지막 로그인일 갱신
        member.setUpdatedDate(LocalDateTime.now());
        memberRepository.save(member);

        // 6. resDto 반환
        return AuthRes.AccessTokenDto.builder()
                .accessToken(jwt)
                .build();
    }

    @Transactional
    public void emailCheck(String email) {
        if (!ValidationUtil.validEmailFormat(email)) {
            throw new RestException(ResponseCode.INVALID);
        }

        Member member = memberRepository.findMemberByEmail(email).orElse(null);
        if (member != null) {
            throw new RestException(ResponseCode.ALREADY_EXISTS);
        }
    }

    @Transactional
    public AuthRes.SignupDto signup(AuthReq.SignupDto reqDto) {
        // 1. 비밀번호 확인 일치 검증
        if (!reqDto.getPassword().equals(reqDto.getPasswordConfirm())) {
            throw new RestException(ResponseCode.PASSWORD_CONFIRM);
        }

        // 2. 이미 존재하는 이메일이 있는지 검증
        if (memberRepository.existsMemberByEmail(reqDto.getEmail())) {
            throw new RestException(ResponseCode.ALREADY_EXISTS_EMAIL);
        }

        Member member = Member.builder()
                .email(reqDto.getEmail())
                .password(passwordEncoder.encode(reqDto.getPassword()))
                .name(reqDto.getName())
                .createdDate(LocalDateTime.now())
                .roles(Collections.singleton(MemberRoleType.USER))
                .accessTokenExpiresAt(LocalDateTime.now())
                .statusCode(MemberStatusType.VERIFIED)
                .build();
        memberRepository.save(member);

        return AuthRes.SignupDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}
