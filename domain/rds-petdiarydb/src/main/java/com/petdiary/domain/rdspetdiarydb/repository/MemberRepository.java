package com.petdiary.domain.rdspetdiarydb.repository;

import com.petdiary.domain.rdscore.repository.ExtendedRepository;
import com.petdiary.domain.rdspetdiarydb.domain.Member;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MemberRepository extends ExtendedRepository<Member, Long>, JpaSpecificationExecutor<Member> {
}
