package com.registrodevendasbackend.controller;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.registrodevendasbackend.DTO.ServiceRecordDTO;
import com.registrodevendasbackend.custom.annotations.UUIDPattern;
import com.registrodevendasbackend.repository.ServiceRepository;
import com.registrodevendasbackend.repository.UserRepository;
import com.registrodevendasbackend.service.ServiceService;

import jakarta.validation.Valid;

import com.registrodevendasbackend.model.Service;
import com.registrodevendasbackend.model.User;

@RestController
@RequestMapping("services")
public class ServiceController {

	@Autowired
	ServiceRepository serviceRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ServiceService serviceService;
	
	@PostMapping("/store")
	public ResponseEntity<Service> saveService(@RequestBody @Valid ServiceRecordDTO serviceRecordDTO) {
		
		var service = new Service();
		
		User user = userRepository.findById(UUID.fromString(serviceRecordDTO.userId())).get();
		
		BeanUtils.copyProperties(serviceRecordDTO, service);
		
		service.setUserId(user.getId());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(serviceRepository.save(service));
	}
	
	@GetMapping("/search/{id}")
	public ResponseEntity<Object> getServiceById( @PathVariable(name = "id") @Valid @UUIDPattern(message = "Id de serviço inválido") String id) {
		
		try {
						
			ServiceRecordDTO serviceRecordDTO = serviceService.getServiceByIdAsServiceRecordDTO(UUID.fromString(id));
			
			return ResponseEntity.status(HttpStatus.OK).body(serviceRecordDTO);
		} catch(NoSuchElementException e) {
			
			e.printStackTrace();
			
			return ResponseEntity.status(401).body("Serviço não encontrado.");
		}
		
	}
}
