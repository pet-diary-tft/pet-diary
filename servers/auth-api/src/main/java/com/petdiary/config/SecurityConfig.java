package com.petdiary.config;

import com.petdiary.properties.CorsProperties;
import com.petdiary.security.MvcAccessDeniedHandler;
import com.petdiary.security.MvcAuthenticationEntryPoint;
import com.petdiary.security.MvcAuthenticationFilter;
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
    private static final String[] PERMIT_AREA = new String[] {
            "/api/v1/auth/**",
            "/api/v1/status/**"
    };

    private final CorsProperties corsProperties;
    private final MvcAuthenticationEntryPoint mvcAuthenticationEntryPoint;
    private final MvcAccessDeniedHandler mvcAccessDeniedHandler;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader(corsProperties.getAllowedHeader());
        configuration.addAllowedMethod(corsProperties.getAllowedMethod());
        configuration.setAllowCredentials(corsProperties.isAllowedCredentials());

        if (corsProperties.getAllowedOrigin() != null) {
            configuration.addAllowedOriginPattern(corsProperties.getAllowedOrigin());
        }

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
                                .accessDeniedHandler(mvcAccessDeniedHandler)
                                .authenticationEntryPoint(mvcAuthenticationEntryPoint)
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers(PERMIT_AREA).permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new MvcAuthenticationFilter(PERMIT_AREA), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
