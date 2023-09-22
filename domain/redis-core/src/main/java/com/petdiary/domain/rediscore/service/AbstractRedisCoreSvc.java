package com.petdiary.domain.rediscore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractRedisCoreSvc {
    protected final RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value) {
        log.debug("[Redis Call] Set key: {} with value: {}", key, value.toString());
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, Duration timeout) {
        log.debug("[Redis Call] Set key: {} with value: {} - timeout: {}sec", key, value.toString(), timeout.getSeconds());
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    public Object get(String key) {
        log.debug("[Redis Call] Get key: {}", key);
        return redisTemplate.opsForValue().get(key);
    }
}
