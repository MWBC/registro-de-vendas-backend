package com.registrodevendasbackend.custom.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.registrodevendasbackend.validator.UUIDPatternValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Constraint(validatedBy={UUIDPatternValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface UUIDPattern {

    String message() default "Id de usuário inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
