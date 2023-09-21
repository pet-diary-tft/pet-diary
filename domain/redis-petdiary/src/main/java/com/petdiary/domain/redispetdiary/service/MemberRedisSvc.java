package com.petdiary.domain.redispetdiary.service;

import com.petdiary.domain.redispetdiary.PetDiaryRedisPrefix;
import com.petdiary.domain.redispetdiary.dto.MemberRedis;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class MemberRedisSvc {
    private final RedisTemplate<String, Object> redisTemplate;

    public void saveMember(MemberRedis.Dto memberRedisDto) {
        redisTemplate.opsForValue().set(PetDiaryRedisPrefix.MEMBER + memberRedisDto.getIdx(), memberRedisDto);
    }

    public void saveMemberAccessToken(String memberAccessToken, MemberRedis.AccessTokenDto memberAccessTokenDto) {
        redisTemplate.opsForValue().set(PetDiaryRedisPrefix.MEMBER_ACCESS_TOKEN + memberAccessToken, memberAccessTokenDto, Duration.ofMinutes(30));
    }

    public MemberRedis.Dto getMemberByAccessToken(String accessToken) {
        MemberRedis.AccessTokenDto accessTokenDto = (MemberRedis.AccessTokenDto) redisTemplate.opsForValue().get(PetDiaryRedisPrefix.MEMBER_ACCESS_TOKEN + accessToken);
        if (accessTokenDto == null) return null;

        return (MemberRedis.Dto) redisTemplate.opsForValue().get(PetDiaryRedisPrefix.MEMBER + accessTokenDto.getMemberIdx());
    }
}
