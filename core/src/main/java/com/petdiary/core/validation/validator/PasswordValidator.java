package com.petdiary.core.validation.validator;

import com.petdiary.core.validation.annotation.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 1. null 또는 빈 값이 아님
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        // 2. 8자 이상 20자 이하
        if (value.length() < 8 || value.length() > 20) {
            return false;
        }

        int count = 0;

        // 3. 영문 대문자 조합
        if (value.matches(".*[A-Z].*")) {
            count++;
        }

        // 4. 영문 소문자 조합
        if (value.matches(".*[a-z].*")) {
            count++;
        }

        // 5. 숫자 조합
        if (value.matches(".*[0-9].*")) {
            count++;
        }

        // 6. 특수문자 조합
        if (value.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            count++;
        }

        // 7. 위 4종류 중 2종류 이상 조합되어 있는지 검증
        return count >= 2;
    }
}
