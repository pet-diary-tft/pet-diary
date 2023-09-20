package com.petdiary.domain.redispetdiary.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("redis.pet-diary")
@Getter @Setter
public class PetDiaryRedisProperties {
    private String host;
    private String password;
    private int port = 6379;
}
