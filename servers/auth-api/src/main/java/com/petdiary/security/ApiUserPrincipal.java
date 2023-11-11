package com.petdiary.security;

import com.petdiary.core.utils.StringUtil;
import com.petdiary.domain.rdspetdiarymembershipdb.domain.Member;
import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberRoleType;
import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberSocialType;
import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberStatusType;
import com.petdiary.domain.redispetdiary.domain.RedisMember;
import com.petdiary.security.oauth2.Oauth2UserPrincipal;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
public class ApiUserPrincipal implements UserDetails {
    private final Long idx;
    private final String email;
    private final String password;
    private final String name;
    private final MemberStatusType status;
    private final MemberSocialType socialType;
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * UserDetails 생성 함수
     * 소셜 로그인일 경우 email은 소셜 이메일로 대체 됩니다.
     * @return Member Entity 객체가 Null이면 Null을 반환합니다.
     */
    public static ApiUserPrincipal create(Member member) {
        if (member == null) return null;

        Set<GrantedAuthority> authorities = member.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());

        return ApiUserPrincipal.builder()
                .idx(member.getIdx())
                .email(member.getSocialType().equals(MemberSocialType.NONE) ? member.getEmail() : member.getSocialEmail())
                .name(member.getName())
                .password(member.getPassword())
                .authorities(authorities)
                .status(member.getStatusCode())
                .socialType(member.getSocialType())
                .build();
    }

    public static ApiUserPrincipal create(RedisMember redisMember) {
        if (redisMember == null) return null;

        Set<GrantedAuthority> authorities = StringUtil.stringToEnumSet(redisMember.getRoles(), MemberRoleType.class)
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());

        return ApiUserPrincipal.builder()
                .idx(redisMember.getIdx())
                .email(redisMember.getEmail())
                .name(redisMember.getName())
                .password(redisMember.getPassword())
                .authorities(authorities)
                .status(MemberStatusType.getByCode(redisMember.getStatus()))
                .socialType(MemberSocialType.getByRegistrationId(redisMember.getSocialType()))
                .build();
    }

    public static ApiUserPrincipal create(Oauth2UserPrincipal oauth2UserPrincipal) {
        if (oauth2UserPrincipal == null) return null;

        return ApiUserPrincipal.builder()
                .idx(oauth2UserPrincipal.getIdx())
                .email(oauth2UserPrincipal.getSocialEmail())
                .name(oauth2UserPrincipal.getName())
                .authorities(oauth2UserPrincipal.getAuthorities())
                .status(oauth2UserPrincipal.getStatus())
                .socialType(oauth2UserPrincipal.getSocialType())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status.equals(MemberStatusType.VERIFIED);
    }

}
