package com.petdiary.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "cors")
@Getter @Setter
public class CorsProperties {
    private List<String> allowedOrigins;
    private String allowedOrigin;
    private String allowedHeader;
    private String allowedMethod;
    private boolean allowedCredentials;
}
