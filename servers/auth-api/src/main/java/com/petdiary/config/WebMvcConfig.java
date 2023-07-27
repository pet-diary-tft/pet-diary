package com.petdiary.config;

import com.petdiary.core.exception.ExceptionInfoConfig;
import com.petdiary.interceptor.LoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public ExceptionInfoConfig getExceptionInfoConfig() {
        return new ExceptionInfoConfig("/config/exception.yml");
    }

    @Bean
    public LoggingInterceptor getLoggingInterceptor() {
        return new LoggingInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoggingInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/api/v1/status/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
