package com.facemind.global.annotation;

import com.facemind.global.validator.ListNotEmptyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ListNotEmptyValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ListNotEmpty {
    String message() default "빈 리스트이면 안됩니다. ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}