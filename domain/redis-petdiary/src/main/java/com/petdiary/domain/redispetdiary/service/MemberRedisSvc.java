package com.petdiary.domain.redispetdiary.service;

import com.petdiary.domain.rediscore.service.AbstractRedisCoreSvc;
import com.petdiary.domain.redispetdiary.PetDiaryRedisPrefix;
import com.petdiary.domain.redispetdiary.dto.MemberRedis;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class MemberRedisSvc extends AbstractRedisCoreSvc {
    public MemberRedisSvc(@Qualifier("petDiaryRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
    }

    public void saveMember(MemberRedis.Dto memberRedisDto) {
        set(PetDiaryRedisPrefix.MEMBER + memberRedisDto.getIdx(), memberRedisDto);
    }

    public void saveMemberAccessToken(String memberAccessToken, MemberRedis.AccessTokenDto memberAccessTokenDto) {
        set(PetDiaryRedisPrefix.MEMBER_ACCESS_TOKEN + memberAccessToken, memberAccessTokenDto, Duration.ofMinutes(30));
    }

    public MemberRedis.Dto getMemberByAccessToken(String accessToken) {
        MemberRedis.AccessTokenDto accessTokenDto = (MemberRedis.AccessTokenDto) get(PetDiaryRedisPrefix.MEMBER_ACCESS_TOKEN + accessToken);
        if (accessTokenDto == null) return null;

        return (MemberRedis.Dto) get(PetDiaryRedisPrefix.MEMBER + accessTokenDto.getMemberIdx());
    }
}
