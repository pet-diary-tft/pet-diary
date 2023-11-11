package com.petdiary.security;

import com.petdiary.domain.rdspetdiarymembershipdb.domain.Member;
import com.petdiary.domain.redispetdiary.domain.RedisMember;
import com.petdiary.domain.redispetdiary.domain.RedisMemberAccessToken;
import com.petdiary.domain.redispetdiary.service.MemberRedisSvc;
import com.petdiary.security.oauth2.Oauth2UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiUserCachingService {
    private final MemberRedisSvc memberRedisSvc;

    public void cachingMember(ApiUserPrincipal principal, String jwt, Long expiredTimeSec) {
        memberRedisSvc.saveMember(RedisMember.builder()
                .idx(principal.getIdx())
                .email(principal.getEmail())
                .password(principal.getPassword())
                .name(principal.getName())
                .status(principal.getStatus().getCode())
                .roles(principal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")))
                .socialType(principal.getSocialType().getRegistrationId())
                .build());
        memberRedisSvc.saveMemberAccessToken(RedisMemberAccessToken.builder()
                .jwt(jwt)
                .memberIdx(principal.getIdx())
                .expiredTime(expiredTimeSec)
                .build());
    }

    public void cachingMember(Member member, String jwt, Long expiredTimeSec) {
        ApiUserPrincipal apiUserPrincipal = ApiUserPrincipal.create(member);
        cachingMember(apiUserPrincipal, jwt, expiredTimeSec);
    }

    public void cachingMember(Oauth2UserPrincipal oauth2UserPrincipal, String jwt, Long expiredTimeSec) {
        ApiUserPrincipal apiUserPrincipal = ApiUserPrincipal.create(oauth2UserPrincipal);
        cachingMember(apiUserPrincipal, jwt, expiredTimeSec);
    }
}
