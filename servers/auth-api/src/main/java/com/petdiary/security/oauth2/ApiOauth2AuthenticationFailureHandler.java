package com.petdiary.security.oauth2;

import com.petdiary.properties.Oauth2ClientProperties;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiOauth2AuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final Oauth2ClientProperties oauth2ClientProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String redirectUri = UriComponentsBuilder.fromUriString(oauth2ClientProperties.getErrorFrontRedirectUri())
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();

        response.sendRedirect(redirectUri);
    }
}
