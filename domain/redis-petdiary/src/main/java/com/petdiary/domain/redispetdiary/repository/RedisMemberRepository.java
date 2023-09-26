package com.petdiary.domain.redispetdiary.repository;

import com.petdiary.domain.redispetdiary.domain.RedisMember;
import org.springframework.data.repository.CrudRepository;

public interface RedisMemberRepository extends CrudRepository<RedisMember, Long> {
}
