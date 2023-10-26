package com.example.shared.enumeration;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidatorConstraint.class)
public @interface EnumValidator {
    Class<? extends Enum<?>> enumClass();
    String message() default "invalid value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}