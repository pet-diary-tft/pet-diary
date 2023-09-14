package com.petdiary.core.validation.annotation;

import com.petdiary.core.validation.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "비밀번호는 8~20자리의 영문 대/소문자, 숫자, 특수문자 등 2종류 이상으로 조합해주세요.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
