package com.petdiary.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class ApiAuthenticationFilter extends OncePerRequestFilter {
    private final String[] permitArea;
    private final ApiUserDetailService apiUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        // 1. 로그인 정보가 필요 없는 경로
        String requestURI = request.getRequestURI();
        if (isPermittedArea(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. 로그인 정보
        UserDetails userDetails = apiUserDetailService.getUserDetails(request);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    private boolean isPermittedArea(String requestURI) {
        return Arrays.stream(permitArea).anyMatch(requestURI::startsWith);
    }
}
