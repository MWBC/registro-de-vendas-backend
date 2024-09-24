package com.registrodevendasbackend.validator;

import java.util.regex.Pattern;

import com.registrodevendasbackend.custom.annotations.UUIDPattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UUIDPatternValidator implements ConstraintValidator<UUIDPattern, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		Pattern uuidRegex = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
		
	    boolean valid = uuidRegex.matcher(value.toString()).matches();

		return valid;
	}

}
