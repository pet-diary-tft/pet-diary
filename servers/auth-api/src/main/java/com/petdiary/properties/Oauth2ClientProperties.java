package com.petdiary.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "oauth2.client")
@Getter @Setter
public class Oauth2ClientProperties {
    private String frontRedirectUri;
    private String errorFrontRedirectUri;
}
