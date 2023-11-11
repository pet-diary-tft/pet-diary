package com.petdiary.security.oauth2;

import com.petdiary.core.utils.StringUtil;
import com.petdiary.domain.rdspetdiarymembershipdb.domain.Member;
import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberRoleType;
import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberSocialType;
import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberStatusType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@Getter
public class Oauth2Attributes {
    private MemberSocialType memberSocialType;
    private String nameAttributeKey;
    private Oauth2UserInfo oauth2UserInfo;

    public Oauth2Attributes(MemberSocialType memberSocialType, String nameAttributeKey, Map<String, Object> attributes) {
        this.memberSocialType = memberSocialType;
        this.nameAttributeKey = nameAttributeKey;
        switch (memberSocialType) {
            case KAKAO -> this.oauth2UserInfo = new KakaoUserInfo(attributes);
            case NAVER -> this.oauth2UserInfo = new NaverUserInfo(attributes);
            default -> this.oauth2UserInfo = new GoogleUserInfo(attributes);
        }
    }

    public Member register() {
        return Member.builder()
                .name(StringUtil.hasText(this.oauth2UserInfo.getNickname()) ? this.oauth2UserInfo.getNickname()
                        : String.format("%s-%s", this.memberSocialType.getRegistrationId(), this.oauth2UserInfo.getId()))
                .createdDate(LocalDateTime.now())
                .socialType(this.memberSocialType)
                .socialId(this.oauth2UserInfo.getId())
                .socialEmail(this.oauth2UserInfo.getEmail())
                .roles(Collections.singleton(MemberRoleType.USER))
                .statusCode(MemberStatusType.VERIFIED)
                .build();
    }

    public Oauth2UserPrincipal getPrincipal(Member member) {
        return new Oauth2UserPrincipal(this, member);
    }
}
