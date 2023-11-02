package com.petdiary.domain.redispetdiary.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter @ToString
@Builder @AllArgsConstructor @NoArgsConstructor
@RedisHash(value = "MemberAccessToken")
public class RedisMemberAccessToken {
    @Id
    private String jwt;
    @Indexed
    private Long memberIdx;

    @TimeToLive
    private long expiredTime;
}
