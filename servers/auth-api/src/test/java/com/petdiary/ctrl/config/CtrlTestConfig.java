package com.petdiary.ctrl.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberStatusType;
import com.petdiary.security.ApiUserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Collections;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class})
public abstract class CtrlTestConfig {
    @Autowired
    protected WebApplicationContext ctx;

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    @BeforeEach
    void setUp(final RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .apply(documentationConfiguration(restDocumentation))
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();

        // Mock Principal
        ApiUserPrincipal principal = ApiUserPrincipal.builder()
                .idx(TestConstants.TEST_USER_IDX)
                .email(TestConstants.TEST_USER_EMAIL)
                .password(TestConstants.TEST_USER_PASSWORD)
                .name(TestConstants.TEST_USER_NAME)
                .authorities(Collections.singleton(new SimpleGrantedAuthority("USER")))
                .status(MemberStatusType.VERIFIED)
                .build();
        Authentication auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    protected String getJsonContent(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
