package com.registrodevendasbackend.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.registrodevendasbackend.DTO.ServiceRecordDTO;
import com.registrodevendasbackend.custom.annotations.UUIDPattern;
import com.registrodevendasbackend.exception.ResourceNotAvailableException;
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
		
		LocalDateTime createdAt = LocalDateTime.now();
		
		service.setUserId(user.getId());
		service.setCreatedAt(Timestamp.valueOf(createdAt));
		service.setUpdatedAt(Timestamp.valueOf(createdAt));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(serviceRepository.save(service));
	}
	
	@GetMapping("/search/{id}")
	public ResponseEntity<Object> getServiceById( @PathVariable @Valid @UUIDPattern(message = "Id de serviço inválido") String id) {
		
		try {

			ServiceRecordDTO serviceRecordDTO = serviceService.getServiceByIdAsServiceRecordDTO(UUID.fromString(id));
			
			return ResponseEntity.status(HttpStatus.OK).body(serviceRecordDTO);
		} catch(NoSuchElementException e) {
			
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado.");
		}catch(ResourceNotAvailableException e) {
			
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
		
	}
	
	@GetMapping("/search/all")
	public ResponseEntity<Object> getServicesByUserId(@RequestParam(required = false) String title, @RequestParam(required = false) String description) {
		
		try {
			
			List<ServiceRecordDTO> filteredServices = serviceService.getFilteredServices(title, description);
			
			return ResponseEntity.status(HttpStatus.OK).body(filteredServices);
		}catch(NoSuchElementException e) {
			
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado.");
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<Object>updateService(@RequestBody @Valid ServiceRecordDTO serviceRecordDTO) {
		
		try {
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			User user = new User();
			
			BeanUtils.copyProperties(auth.getPrincipal(), user);
			
			LocalDateTime updatedAt = LocalDateTime.now();

			Service serviceDB = serviceRepository.findByIdAndUserId(serviceRecordDTO.id(), user.getId()).get();
			
			serviceDB.setBasePrice(serviceRecordDTO.basePrice());
			serviceDB.setTitle(serviceRecordDTO.title());
			serviceDB.setDescription(serviceRecordDTO.description());
			serviceDB.setUpdatedAt(Timestamp.valueOf(updatedAt));
			
			serviceRepository.save(serviceDB);
			
			return ResponseEntity.status(HttpStatus.CREATED).body("Serviço atualizado com sucesso.");
		}catch(NoSuchElementException e) {
			
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado.");
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteService(@PathVariable @Valid @UUIDPattern(message = "Id de serviço inválido") String id) {
		
		try {
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			User user = new User();
			
			BeanUtils.copyProperties(auth.getPrincipal(), user);
			
			LocalDateTime updatedAt = LocalDateTime.now();
			
			Service service = serviceRepository.findByIdAndUserId(UUID.fromString(id), user.getId()).get();
			service.setDeleted(true);
			service.setUpdatedAt(Timestamp.valueOf(updatedAt));
			
			serviceRepository.save(service);
			
			return ResponseEntity.status(HttpStatus.CREATED).body("Serviço deletado com sucesso.");
		}catch(NoSuchElementException e) {
			
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado.");
	
		}
	}
}
