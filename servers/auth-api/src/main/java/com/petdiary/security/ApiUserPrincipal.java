package com.petdiary.security;

import com.petdiary.domain.rdspetdiarymembershipdb.domain.Member;
import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberStatusType;
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
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * UserDetails 생성 함수
     * @return Member Entity 객체가 Null이면 Null을 반환합니다.
     */
    public static ApiUserPrincipal create(Member member) {
        if (member == null) return null;

        Set<GrantedAuthority> authorities = member.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());

        return ApiUserPrincipal.builder()
                .idx(member.getIdx())
                .email(member.getEmail())
                .name(member.getName())
                .password(member.getPassword())
                .authorities(authorities)
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
