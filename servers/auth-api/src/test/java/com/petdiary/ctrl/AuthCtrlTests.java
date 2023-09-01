package com.petdiary.ctrl;

import com.petdiary.controller.AuthCtrl;
import com.petdiary.dto.res.AuthRes;
import com.petdiary.service.AuthSvc;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthCtrl.class)
public class AuthCtrlTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthSvc authSvc;

    @Test
    public void testLogin() throws Exception {
        AuthRes.LoginDto mockLoginDto = AuthRes.LoginDto.builder()
                .idx(1L)
                .accessToken("mockToken")
                .refreshToken("mockRefreshToken")
                .build();

        when(authSvc.login(any(), any(), any())).thenReturn(mockLoginDto);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\",\"password\":\"password\"}"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.result.code").value("2000000"))
                .andExpect(jsonPath("$.body.accessToken").value("mockToken"))
                .andExpect(jsonPath("$.body.refreshToken").value("mockRefreshToken"))
                .andDo(print());
    }
}
