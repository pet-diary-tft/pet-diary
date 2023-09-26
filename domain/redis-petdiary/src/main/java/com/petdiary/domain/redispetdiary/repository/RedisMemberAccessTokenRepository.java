package com.petdiary.domain.redispetdiary.repository;

import com.petdiary.domain.redispetdiary.domain.RedisMemberAccessToken;
import org.springframework.data.repository.CrudRepository;

public interface RedisMemberAccessTokenRepository extends CrudRepository<RedisMemberAccessToken, String> {
}
