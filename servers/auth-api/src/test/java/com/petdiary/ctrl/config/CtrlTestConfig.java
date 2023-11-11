package com.petdiary.ctrl.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberSocialType;
import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberStatusType;
import com.petdiary.security.ApiUserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
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
                .socialType(MemberSocialType.KAKAO)
                .build();
        Authentication auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    protected String getJsonContent(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    protected List<FieldDescriptor> commonRes() {
        return Arrays.asList(
                fieldWithPath("result.httpStatusCode").description("HTTP 상태 코드"),
                fieldWithPath("result.code").description("응답 코드"),
                fieldWithPath("result.message").description("응답 메시지"),
                fieldWithPath("body").description("응답 상세").optional()
        );
    }

    protected List<FieldDescriptor> commonResBodyFields(FieldDescriptor... fieldDescriptors) {
        List<FieldDescriptor> commonFields = Arrays.asList(
                fieldWithPath("result.httpStatusCode").description("HTTP 상태 코드"),
                fieldWithPath("result.code").description("응답 코드"),
                fieldWithPath("result.message").description("응답 메시지")
        );

        return Stream.concat(commonFields.stream(), Stream.of(fieldDescriptors))
                .collect(Collectors.toList());
    }

    /**
     * 클래스명과 메서드명을 참고해 자동으로 문서명을 작성해주는 유틸 함수
     */
    protected String getDocumentName() {
        // 현재 실행 중인 메서드와 클래스의 정보를 가져옴
        StackTraceElement currentElement = Thread.currentThread().getStackTrace()[2];
        String className = currentElement.getClassName();
        String methodName = currentElement.getMethodName();

        // "CtrlTests", "CtrlTest", "Tests", "Test" 접미사 제거
        className = className.substring(className.lastIndexOf('.') + 1).replaceAll("(CtrlTests?|Tests?)$", "");

        // "test" 접두사 제거
        methodName = methodName.replaceFirst("^test", "");

        // CamelCase를 hyphen-case로 변환
        String hyphenClassName = camelToHyphen(className);
        String hyphenMethodName = camelToHyphen(methodName);

        return hyphenClassName + "-" + hyphenMethodName + "-doc";
    }

    private static String camelToHyphen(String str) {
        return str
                .replaceAll("(.)(\\p{Upper})", "$1-$2")
                .toLowerCase();
    }
}
