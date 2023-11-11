package com.petdiary.security.oauth2;

import com.petdiary.domain.rdspetdiarymembershipdb.domain.Member;
import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberRoleType;
import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberSocialType;
import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberStatusType;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collections;

@Getter
public class Oauth2UserPrincipal extends DefaultOAuth2User {
    private final Long idx;
    private final String name;
    private final String socialId;
    private final String socialEmail;
    private final MemberSocialType socialType;
    private final MemberStatusType status;

    public Oauth2UserPrincipal(Oauth2Attributes oauth2Attributes, Member member) {
        super(Collections.singleton(new SimpleGrantedAuthority(MemberRoleType.USER.name())), oauth2Attributes.getOauth2UserInfo().attributes, oauth2Attributes.getNameAttributeKey());
        this.idx = member.getIdx();
        this.name = member.getName();
        this.socialId = member.getSocialId();
        this.socialEmail = member.getSocialEmail();
        this.socialType = member.getSocialType();
        this.status = member.getStatusCode();
    }
}
