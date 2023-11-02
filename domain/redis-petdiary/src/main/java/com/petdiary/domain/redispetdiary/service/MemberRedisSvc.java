package com.petdiary.domain.redispetdiary.service;

import com.petdiary.domain.redispetdiary.domain.RedisMember;
import com.petdiary.domain.redispetdiary.domain.RedisMemberAccessToken;
import com.petdiary.domain.redispetdiary.repository.RedisMemberAccessTokenRepository;
import com.petdiary.domain.redispetdiary.repository.RedisMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberRedisSvc {
    private final RedisMemberRepository redisMemberRepository;
    private final RedisMemberAccessTokenRepository redisMemberAccessTokenRepository;

    public void saveMember(RedisMember redisMember) {
        redisMemberRepository.save(redisMember);
    }

    public void saveMemberAccessToken(RedisMemberAccessToken redisMemberAccessToken) {
        redisMemberAccessTokenRepository.save(redisMemberAccessToken);
    }

    public RedisMember getMemberByAccessToken(String jwt) {
        RedisMemberAccessToken redisMemberAccessToken = redisMemberAccessTokenRepository.findById(jwt).orElse(null);
        if (redisMemberAccessToken == null) return null;
        return redisMemberRepository.findById(redisMemberAccessToken.getMemberIdx()).orElse(null);
    }

    public void deleteAllAccessTokenByMemberIdx(Long memberIdx) {
        List<RedisMemberAccessToken> redisMemberAccessTokenList = redisMemberAccessTokenRepository.findByMemberIdx(memberIdx);
        redisMemberAccessTokenList.forEach(redisMemberAccessToken -> redisMemberAccessTokenRepository.deleteById(redisMemberAccessToken.getJwt()));
    }
}
