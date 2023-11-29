package com.petdiary.security;

import com.petdiary.vo.MemberVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiUserDetailService implements UserDetailsService {
    // TODO
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return ApiUserPrincipal.create(new MemberVo());
    }

    /** TODO:
     * 헤더 내 토큰을 읽어 UserDetails를 가져오는 함수
     */
    public UserDetails getUserDetails(HttpServletRequest request) {
        return loadUserByUsername("test");
    }
}
