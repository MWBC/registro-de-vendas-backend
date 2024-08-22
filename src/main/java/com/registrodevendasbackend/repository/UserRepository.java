package com.registrodevendasbackend.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.registrodevendasbackend.model.User;

public interface UserRepository extends CrudRepository<User, UUID> {

}
