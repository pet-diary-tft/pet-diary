package com.petdiary.config;

import com.petdiary.properties.CorsProperties;
import com.petdiary.security.*;
import com.petdiary.security.oauth2.ApiOauth2AuthenticationFailureHandler;
import com.petdiary.security.oauth2.ApiOauth2AuthenticationSuccessHandler;
import com.petdiary.security.oauth2.ApiOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        // @Secured 애노테이션을 사용하여 인가 처리를 하고 싶을때 사용하는 옵션이다.
        securedEnabled = false,
        // @RolesAllowed 애노테이션을 사용하여 인가 처리를 하고 싶을때 사용하는 옵션이다.
        jsr250Enabled = false,
        // @PreAuthorize, @PostAuthorize 애노테이션을 사용하여 인가 처리를 하고 싶을때 사용하는 옵션이다.
        prePostEnabled = true
)
@RequiredArgsConstructor
public class SecurityConfig {
    /**
     * 로그인이 필요없으며 컨트롤러에 Principal 정보를 전달하지않음
     */
    private static final String[] PERMIT_AREA = new String[] {
            "/static/**",
            "/swagger-ui.html",
            "/api/v1/auth/**",
            "/api/v1/status/**",
            "/actuator/prometheus",
            "/oauth2/**"
    };

    /**
     * 로그인이 필요없지만 컨트롤러에 Principal 정보를 전달함
     */
    private static final String[] PERMIT_AREA_WITH_PRINCIPAL = new String[] {
            "/api/v1/user/my"
    };

    private final CorsProperties corsProperties;
    private final ApiAuthenticationEntryPoint apiAuthenticationEntryPoint;
    private final ApiAccessDeniedHandler apiAccessDeniedHandler;
    private final ApiUserDetailService apiUserDetailService;
    private final ApiOauth2AuthenticationSuccessHandler apiOauth2AuthenticationSuccessHandler;
    private final ApiOauth2AuthenticationFailureHandler apiOauth2AuthenticationFailureHandler;
    private final ApiOauth2UserService apiOauth2UserService;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedHeader(corsProperties.getAllowedHeader());
        configuration.addAllowedMethod(corsProperties.getAllowedMethod());
        configuration.setAllowCredentials(corsProperties.isAllowedCredentials());

        if (corsProperties.getAllowedOrigins() != null) {
            corsProperties.getAllowedOrigins().forEach(configuration::addAllowedOriginPattern);
        }

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .accessDeniedHandler(apiAccessDeniedHandler)
                                .authenticationEntryPoint(apiAuthenticationEntryPoint)
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers(PERMIT_AREA).permitAll()
                                .requestMatchers(PERMIT_AREA_WITH_PRINCIPAL).permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Configurer -> oauth2Configurer
                        .authorizationEndpoint(authEndpointConfig ->
                                authEndpointConfig
                                        .baseUri("/oauth2/authorization")
                        )
                        .redirectionEndpoint(redirectionEndpointConfig ->
                                redirectionEndpointConfig
                                        .baseUri("/oauth2/code/*")
                        )
                        .userInfoEndpoint(userInfoEndpointConfig ->
                                userInfoEndpointConfig
                                        .userService(apiOauth2UserService)
                        )
                        .successHandler(apiOauth2AuthenticationSuccessHandler)
                        .failureHandler(apiOauth2AuthenticationFailureHandler)
                )
                .addFilterBefore(new ApiAuthenticationFilter(PERMIT_AREA, apiUserDetailService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
