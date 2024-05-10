package com.facemind.global.validator;

import com.facemind.global.annotation.ListNotEmpty;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListNotEmptyValidator implements ConstraintValidator<ListNotEmpty, List<String>> {
    @Override
    public void initialize(ListNotEmpty constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List value, ConstraintValidatorContext context) {
        return !value.isEmpty();
    }
}
