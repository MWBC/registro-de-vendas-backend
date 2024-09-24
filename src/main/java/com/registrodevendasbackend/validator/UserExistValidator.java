package com.registrodevendasbackend.validator;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.registrodevendasbackend.custom.annotations.UserExist;
import com.registrodevendasbackend.repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserExistValidator implements ConstraintValidator<UserExist, String> {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		try {
			
			userRepository.findById(UUID.fromString(value)).get();
			
			return true;
		}catch(NoSuchElementException e) {
			
			return false;			
		}
		
	}

}
