package com.petdiary.domain.redispetdiary.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter @ToString
@Builder @AllArgsConstructor @NoArgsConstructor
@RedisHash(value = "Member")
public class RedisMember {
    @Id
    private Long idx;
    private String email;
    private String password;
    private String name;
    private Byte status;
    private String roles;
    private Integer tokenVersion;
}
