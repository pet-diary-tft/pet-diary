package com.petdiary.core.validation.validator;

import com.petdiary.core.validation.annotation.ByteSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ByteSizeValidator implements ConstraintValidator<ByteSize, String> {
    private int min;
    private int max;

    @Override
    public void initialize(ByteSize constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        int byteSize = 0;
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c > 127) {
                byteSize += 2;
            } else {
                byteSize++;
            }
        }
        return min <= byteSize && byteSize <= max;
    }
}
