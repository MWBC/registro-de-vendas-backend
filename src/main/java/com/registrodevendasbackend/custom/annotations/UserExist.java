package com.registrodevendasbackend.custom.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.registrodevendasbackend.validator.UserExistValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserExistValidator.class)
public @interface UserExist {

	String message() default "Usuário não encontrado";
	
	Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
