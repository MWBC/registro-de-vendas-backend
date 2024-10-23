package com.registrodevendasbackend.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.registrodevendasbackend.model.User;

public interface UserRepository extends CrudRepository<User, UUID> {
	
	UserDetails findByEmail(String email); 

}
