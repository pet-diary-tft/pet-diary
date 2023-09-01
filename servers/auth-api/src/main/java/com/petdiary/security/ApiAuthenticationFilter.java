package com.petdiary.security;

import com.petdiary.core.utils.HttpUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class ApiAuthenticationFilter extends OncePerRequestFilter {
    private final String[] permitArea;
    private final ApiUserDetailService apiUserDetailService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        // 1. 로그인 정보가 필요 없는 경로
        String requestURI = request.getRequestURI();
        if (isPermittedArea(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. 로그인 정보
        try {
            UserDetails userDetails = apiUserDetailService.getUserDetails(request);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception e) {
            String clientId = HttpUtil.getClientIp(request);
            if (!clientId.equals("127.0.0.1")) {
                log.warn(String.format("[Filter Exception]: [%s]: [%s]: [%s]", clientId, request.getRequestURI(), e.getMessage()));
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPermittedArea(String requestURI) {
        return Arrays.stream(permitArea).anyMatch(pattern -> pathMatcher.match(pattern, requestURI));
    }
}
