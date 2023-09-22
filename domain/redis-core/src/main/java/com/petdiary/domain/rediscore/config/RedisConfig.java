package com.petdiary.domain.rediscore.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration("RedisCoreConfig")
@RequiredArgsConstructor
public class RedisConfig {
    @Bean("coreRedisObjectMapper")
    public ObjectMapper objectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        return om;
    }

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
    public Jackson2JsonRedisSerializer<Object> jsonRedisSerializer(
            @Qualifier("coreRedisObjectMapper") ObjectMapper redisObjectMapper
    ) {
        return new Jackson2JsonRedisSerializer<>(redisObjectMapper, Object.class);
    }
}
