package com.petdiary.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petdiary.interceptor.LoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final Environment environment;

    @Bean
    public LoggingInterceptor getLoggingInterceptor() {
        return new LoggingInterceptor();
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoggingInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/api/v1/status/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // server env profiles
        if (isProfileActive("prod", "dev")) {
            registry.addResourceHandler("/static/swagger-ui/**").addResourceLocations("classpath:/static/swagger-ui/");
        }
        // local env profiles
        else {
            registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/static/swagger-ui.html");
            registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        }
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    private boolean isProfileActive(String... profiles) {
        return Arrays.stream(environment.getActiveProfiles())
                .anyMatch(activeProfile -> Arrays.asList(profiles).contains(activeProfile));
    }
}
