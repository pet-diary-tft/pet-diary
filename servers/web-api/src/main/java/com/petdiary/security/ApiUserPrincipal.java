package com.petdiary.security;

import com.petdiary.vo.MemberVo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Getter
@Builder
public class ApiUserPrincipal implements UserDetails {
    private final Long idx;
    private final String email;
    private final String password;
    private final String name;
    private final Collection<? extends GrantedAuthority> authorities;

    public static ApiUserPrincipal create(MemberVo memberVo) {
        if (memberVo == null) return null;

        Set<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("USER"));

        return ApiUserPrincipal.builder()
                .idx(memberVo.getIdx())
                .email(memberVo.getEmail())
                .name(memberVo.getName())
                .password(null)
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
        return true;
    }
}
