package com.petdiary.clients.petdiaryauthclient.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("pet-diary-auth-client")
@Getter @Setter
public class AuthClientProperties {
    private String baseUrl;
    private String tokenType;
}
