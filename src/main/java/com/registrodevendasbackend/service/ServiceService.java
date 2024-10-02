package com.registrodevendasbackend.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.registrodevendasbackend.DTO.ServiceRecordDTO;
import com.registrodevendasbackend.repository.ServiceRepository;

import jakarta.transaction.Transactional;

@Service
public class ServiceService {

	@Autowired
	private ServiceRepository serviceRepository;

	@Transactional
	public com.registrodevendasbackend.model.Service getServiceById(UUID id) {
		
		return serviceRepository.findById(id).get();
	}
	
	public ServiceRecordDTO getServiceByIdAsServiceRecordDTO(UUID id) {
		
		com.registrodevendasbackend.model.Service service = serviceRepository.findById(id).get();
		
		UUID serviceId = service.getId();
		
		String title = service.getTitle();
		
		String description = service.getDescription();
		
		BigDecimal basePrice = service.getBasePrice();
		
		String userId = service.getUserId().toString();
		
		Timestamp createdAt = service.getCreatedAt();
		
		Timestamp updatedAt = service.getUpdatedAt();
		
		return new ServiceRecordDTO(serviceId, title, description, basePrice, userId, null, false, createdAt, updatedAt);
	}
	
	public ServiceRepository getServiceRepository() {
		return serviceRepository;
	}

	public void setServiceRepository(ServiceRepository serviceRepository) {
		this.serviceRepository = serviceRepository;
	}
}
