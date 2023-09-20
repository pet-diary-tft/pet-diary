package com.petdiary.domain.redispetdiary.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.petdiary.domain.redispetdiary.properties.PetDiaryRedisProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration("petDiaryRedisConfig")
@RequiredArgsConstructor
public class RedisConfig {
    private final PetDiaryRedisProperties petDiaryRedisProperties;

    @Bean("petDiaryJedisConnectionFactory")
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(
                petDiaryRedisProperties.getHost(),
                petDiaryRedisProperties.getPort()
        );
        configuration.setPassword(petDiaryRedisProperties.getPassword());
        return new JedisConnectionFactory(configuration);
    }

    /**
     * 문자열 형태 직렬화
     */
    @Bean("petDiaryStringRedisSerializer")
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    /**
     * Json 형태 직렬화
     */
    @Bean("petDiaryJsonRedisSerializer")
    public Jackson2JsonRedisSerializer<Object> jsonRedisSerializer() {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        return new Jackson2JsonRedisSerializer<>(om, Object.class);
    }

    @Bean("petDiaryRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(
            @Qualifier("petDiaryJedisConnectionFactory") JedisConnectionFactory jedisConnectionFactory,
            @Qualifier("petDiaryStringRedisSerializer") StringRedisSerializer stringRedisSerializer,
            @Qualifier("petDiaryJsonRedisSerializer") Jackson2JsonRedisSerializer<Object> jsonRedisSerializer
    ) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        return redisTemplate;
    }
}
