package com.petdiary.domain.rdspetdiarymembershipdb.repository;

import com.petdiary.domain.rdscore.repository.ExtendedRepository;
import com.petdiary.domain.rdspetdiarymembershipdb.domain.Member;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MemberRepository extends ExtendedRepository<Member, Long>, JpaSpecificationExecutor<Member> {
    Optional<Member> findMemberByEmail(String email);
    boolean existsMemberByEmail(String email);

    @Modifying
    @Query("UPDATE Member m SET m.password = :encodedPassword, m.accessTokenExpiresAt = :updatedDate, m.updatedDate = :updatedDate WHERE m.idx = :idx")
    int changePassword(Long idx, String encodedPassword, LocalDateTime updatedDate);
}
