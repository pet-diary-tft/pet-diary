package com.petdiary.domain.rdspetdiarymembershipdb.repository;

import com.petdiary.domain.rdscore.repository.ExtendedRepository;
import com.petdiary.domain.rdspetdiarymembershipdb.domain.Member;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MemberRepository extends ExtendedRepository<Member, Long>, JpaSpecificationExecutor<Member> {
}
