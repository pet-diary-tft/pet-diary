package com.petdiary.domain.redispetdiary.repository;

import com.petdiary.domain.redispetdiary.domain.RedisMemberAccessToken;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RedisMemberAccessTokenRepository extends CrudRepository<RedisMemberAccessToken, String> {
    List<RedisMemberAccessToken> findByMemberIdx(Long memberIdx);
}
