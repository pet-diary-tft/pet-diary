package com.petdiary.domain.rdspetdiarymembershipdb.repository;


import com.petdiary.domain.rdscore.repository.ExtendedRepository;
import com.petdiary.domain.rdspetdiarymembershipdb.domain.MemberRefreshToken;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MemberRefreshTokenRepository extends ExtendedRepository<MemberRefreshToken, UUID>, JpaSpecificationExecutor<MemberRefreshToken> {
    MemberRefreshToken findByMemberIdxAndRefreshToken(Long memberIdx, String refreshToken);

    @Query("SELECT mrt FROM MemberRefreshToken mrt WHERE mrt.memberIdx=:memberIdx AND mrt.userAgent=:userAgent AND mrt.clientIp=:clientIp")
    List<MemberRefreshToken> getList(Long memberIdx, String userAgent, String clientIp);

    @Modifying
    @Query("DELETE FROM MemberRefreshToken mrt WHERE mrt.memberIdx=:memberIdx")
    int clearRefreshToken(Long memberIdx);
}
