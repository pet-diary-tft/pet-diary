package com.petdiary.service;

import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberSocialType;
import com.petdiary.domain.rdspetdiarymembershipdb.repository.MemberRepository;
import com.petdiary.domain.redispetdiary.service.MemberRedisSvc;
import com.petdiary.dto.req.UserReq;
import com.petdiary.dto.res.UserRes;
import com.petdiary.exception.ApiResponseCode;
import com.petdiary.exception.ApiRestException;
import com.petdiary.security.ApiUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserSvc {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberRedisSvc memberRedisSvc;

    public UserRes.MyDto my(ApiUserPrincipal principal) {
        if (principal == null) {
            return UserRes.MyDto.builder()
                    .loggedIn(false)
                    .build();
        }

        return UserRes.MyDto.builder()
                .loggedIn(true)
                .idx(principal.getIdx())
                .email(principal.getEmail())
                .name(principal.getName())
                .socialType(principal.getSocialType().getRegistrationId())
                .build();
    }

    @Transactional
    public void changePassword(ApiUserPrincipal principal, UserReq.ChangePasswordDto reqDto) {
        // 1. 소셜 로그인 회원
        if (!principal.getSocialType().equals(MemberSocialType.NONE)) {
            throw new ApiRestException(ApiResponseCode.IS_SOCIAL_LOGIN_MEMBER);
        }

        // 2. 이전 비밀번호 확인
        if (!passwordEncoder.matches(reqDto.getOldPassword(), principal.getPassword())) {
            throw new ApiRestException(ApiResponseCode.OLD_PASSWORD_CONFIRM);
        }

        // 3. 비밀번호 확인 일치 검증
        if (!reqDto.getNewPassword().equals(reqDto.getNewPasswordConfirm())) {
            throw new ApiRestException(ApiResponseCode.PASSWORD_CONFIRM);
        }

        // 4. 비밀번호 변경 및 기존 토큰 무효화
        int updateCount = memberRepository.changePassword(principal.getIdx(), passwordEncoder.encode(reqDto.getNewPassword()), LocalDateTime.now());
        if (updateCount < 1) {
            throw new ApiRestException(ApiResponseCode.MEMBER_NOT_FOUND);
        }
        memberRedisSvc.deleteAllAccessTokenByMemberIdx(principal.getIdx());
    }
}
