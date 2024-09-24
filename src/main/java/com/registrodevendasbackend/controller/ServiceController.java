package com.registrodevendasbackend.controller;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.registrodevendasbackend.DTO.ServiceRecordDTO;
import com.registrodevendasbackend.repository.ServiceRepository;
import com.registrodevendasbackend.repository.UserRepository;

import jakarta.validation.Valid;

import com.registrodevendasbackend.model.Service;
import com.registrodevendasbackend.model.User;

@RestController
public class ServiceController {

	@Autowired
	ServiceRepository serviceRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/services/store")
	public ResponseEntity<Service> saveService(@RequestBody @Valid ServiceRecordDTO serviceRecordDTO) {
		
		var service = new Service();
		
		User user = userRepository.findById(UUID.fromString(serviceRecordDTO.userId())).get();
		
		BeanUtils.copyProperties(serviceRecordDTO, service);
		
		service.setUser(user);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(serviceRepository.save(service));
	}
}
