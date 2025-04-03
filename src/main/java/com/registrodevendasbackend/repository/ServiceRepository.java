package com.registrodevendasbackend.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.registrodevendasbackend.model.Service;

public interface ServiceRepository extends CrudRepository<Service, UUID>{

	Optional<Service> findByIdAndUserId(UUID id, UUID userId);
}
