package com.petdiary.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "auth.jwt")
@Getter @Setter
public class AuthJwtProperties {
    private String type;
    private String secret;
    private Integer expiryInMs;
    private Integer refreshExpiryInDays;
}
