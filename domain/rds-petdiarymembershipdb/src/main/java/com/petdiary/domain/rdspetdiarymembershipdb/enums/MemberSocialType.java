package com.petdiary.domain.rdspetdiarymembershipdb.enums;

import com.petdiary.core.utils.StringUtil;

public enum MemberSocialType {
    NONE("none", "일반가입"),
    KAKAO("kakao", "카카오"),
    GOOGLE("google", "구글"),
    NAVER("naver", "네이버"),
    ;

    private final String registrationId;
    private final String desc;

    MemberSocialType(String registrationId, String desc) {
        this.registrationId = registrationId;
        this.desc = desc;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public String getDesc() {
        return desc;
    }

    public static MemberSocialType getByRegistrationId(String registrationId) {
        if (!StringUtil.hasText(registrationId)) return null;
        for (MemberSocialType memberSocialType: MemberSocialType.values()) {
            if (memberSocialType.registrationId.equals(registrationId)) return memberSocialType;
        }
        return null;
    }
}
