package com.petdiary.domain.rediscore.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration("RedisCoreConfig")
@RequiredArgsConstructor
public class RedisConfig {
    /**
     * 문자열 형태 직렬화
     */
    @Bean("coreStringRedisSerializer")
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    /**
     * Json 형태 직렬화
     */
    @Bean("coreJsonRedisSerializer")
    public Jackson2JsonRedisSerializer<Object> jsonRedisSerializer() {
        return new Jackson2JsonRedisSerializer<>(Object.class);
    }
}
