package com.petdiary.domain.redispetdiary.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter @ToString
@Builder @AllArgsConstructor @NoArgsConstructor
@RedisHash(value = "MemberAccessToken")
public class RedisMemberAccessToken {
    @Id
    private String jwt;
    private Long memberIdx;
    private Integer tokenVersion;

    @TimeToLive
    private long expiredTime;
}
