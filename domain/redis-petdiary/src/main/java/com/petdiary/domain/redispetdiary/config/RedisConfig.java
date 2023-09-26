package com.petdiary.domain.redispetdiary.config;

import com.petdiary.domain.redispetdiary.properties.PetDiaryRedisProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration("petDiaryRedisConfig")
@RequiredArgsConstructor
public class RedisConfig {
    private final PetDiaryRedisProperties petDiaryRedisProperties;

    @Bean("petDiaryRedisConnectionFactory")
    LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(
                petDiaryRedisProperties.getHost(),
                petDiaryRedisProperties.getPort()
        );
        configuration.setPassword(petDiaryRedisProperties.getPassword());
        return new LettuceConnectionFactory(configuration);
    }

    @Bean("petDiaryRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(
            @Qualifier("petDiaryRedisConnectionFactory") LettuceConnectionFactory redisConnectionFactory
    ) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
