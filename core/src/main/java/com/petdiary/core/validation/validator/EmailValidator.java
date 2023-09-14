package com.petdiary.core.validation.validator;

import com.petdiary.core.utils.ValidationUtil;
import com.petdiary.core.validation.annotation.Email;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return ValidationUtil.validEmailFormat(value);
    }
}
