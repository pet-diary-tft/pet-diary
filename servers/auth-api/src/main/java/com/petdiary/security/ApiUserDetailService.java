package com.petdiary.security;

import com.petdiary.core.exception.ResponseCode;
import com.petdiary.core.utils.DateUtil;
import com.petdiary.core.utils.HttpUtil;
import com.petdiary.domain.rdspetdiarymembershipdb.domain.Member;
import com.petdiary.domain.rdspetdiarymembershipdb.repository.MemberRepository;
import com.petdiary.properties.AuthJwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApiUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final AuthJwtProperties authJwtProperties;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findMemberByEmail(email)
                .map(ApiUserPrincipal::create)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found."));
    }

    /**
     * 헤더 내 토큰을 읽어 UserDetails를 가져오는 함수
     */
    @Transactional(readOnly = true)
    public UserDetails getUserDetails(HttpServletRequest request) {
        // 1. 헤더에서 jwt 추출
        String jwt = HttpUtil.getAuthorizationHeaderValue(request, authJwtProperties.getType());
        if (jwt == null) {
            request.setAttribute("ymlKey", ResponseCode.UNAUTHORIZED.getKey());
            throw new UsernameNotFoundException("Member jwt not found.");
        }

        // 2. jwt 값 검증
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(authJwtProperties.getSecret().getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(jwt);
        } catch (ExpiredJwtException e) {
            request.setAttribute("ymlKey", ResponseCode.EXPIRED_JWT.getKey());
            throw new UsernameNotFoundException("Expired jwt.");
        } catch (UnsupportedJwtException e) {
            request.setAttribute("ymlKey", ResponseCode.INVALID_JWT.getKey());
            throw new UsernameNotFoundException("Unsupported jwt.");
        } catch (IllegalArgumentException e) {
            request.setAttribute("ymlKey", ResponseCode.INVALID_JWT.getKey());
            throw new UsernameNotFoundException("Not found jwt.");
        } catch (JwtException e) {
            request.setAttribute("ymlKey", ResponseCode.INVALID_JWT.getKey());
            throw new UsernameNotFoundException("Invalid jwt.");
        } catch (Exception e) {
            request.setAttribute("ymlKey", ResponseCode.INVALID_JWT.getKey());
            throw new UsernameNotFoundException("Unknown jwt error.");
        }

        // 3. principal 생성
        ApiUserPrincipal userPrincipal = null;
        LocalDateTime accessTokenExpiresAt = null;
        try {
            // 3-1. jwt에서 추출한 idx로 관리자 검색
            Long userIdx = Long.valueOf(claimsJws.getBody().getSubject());
            Member member = memberRepository.findById(userIdx).orElse(null);

            // 3-2. entity -> principal
            userPrincipal = ApiUserPrincipal.create(member);

            // 3-3 액세스 토큰 검증을 위한 값
            accessTokenExpiresAt = member == null ? null : member.getAccessTokenExpiresAt();
        } catch (Exception ignore) {}

        // 4. 액세스 토큰 검증
        if (accessTokenExpiresAt != null) {
            LocalDateTime jwtExpiration = DateUtil.convertToLocalDateTime(claimsJws.getBody().getExpiration());
            if (jwtExpiration.isBefore(accessTokenExpiresAt)) {
                request.setAttribute("ymlKey", ResponseCode.EXPIRED_JWT.getKey());
                throw new UsernameNotFoundException("Expired jwt.");
            }
        }

        // 5. principal 검증
        if (userPrincipal == null) throw new UsernameNotFoundException("User not found.");
        if (!userPrincipal.isEnabled()) {
            request.setAttribute("ymlKey", ResponseCode.INVALID_JWT.getKey());
            throw new UsernameNotFoundException(String.format("%s is disabled(status: %s) member.",
                    userPrincipal.getUsername(), userPrincipal.getStatus().name()));
        }

        return userPrincipal;
    }
}
