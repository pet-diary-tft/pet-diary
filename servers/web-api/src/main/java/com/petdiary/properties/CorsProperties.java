package com.petdiary.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "cors")
@Getter @Setter
public class CorsProperties {
    private List<String> allowedOrigins;
    private String allowedHeader;
    private String allowedMethod;
    private boolean allowedCredentials;
}
