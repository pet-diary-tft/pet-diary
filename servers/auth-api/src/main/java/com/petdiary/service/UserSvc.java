package com.petdiary.service;

import com.petdiary.dto.res.UserRes;
import com.petdiary.security.ApiUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSvc {

    public UserRes.MyDto my(ApiUserPrincipal principal) {
        if (principal == null) {
            return UserRes.MyDto.builder()
                    .loggedIn(false)
                    .build();
        }

        return UserRes.MyDto.builder()
                .loggedIn(true)
                .idx(principal.getIdx())
                .email(principal.getEmail())
                .name(principal.getName())
                .build();
    }
}
