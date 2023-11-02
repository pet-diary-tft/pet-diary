package com.petdiary.ctrl.factory;

import com.petdiary.dto.req.UserReq;

public class UserDtoFactory {
    public static UserReq.ChangePasswordDto createChangePasswordReqDto() {
        UserReq.ChangePasswordDto reqDto = new UserReq.ChangePasswordDto();
        reqDto.setOldPassword("oldPassword");
        reqDto.setNewPassword("newPassword");
        reqDto.setNewPassword("newPassword");
        return reqDto;
    }
}
