package com.petdiary.domain.rdscore.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public interface ExtendedRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    /**
     * count 쿼리없이 limit 문법이 필요할 때 사용하기 위한 함수
     */
    List<T> findAllWithoutCount(Specification<T> spec, Pageable pageable);
}
