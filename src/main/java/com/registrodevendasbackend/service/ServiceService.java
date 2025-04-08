package com.registrodevendasbackend.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.registrodevendasbackend.DTO.ServiceRecordDTO;
import com.registrodevendasbackend.exception.ResourceNotAvailableException;
import com.registrodevendasbackend.model.Role;
import com.registrodevendasbackend.model.User;
import com.registrodevendasbackend.repository.ServiceRepository;
import com.registrodevendasbackend.specifications.ServiceSpecifications;

import jakarta.transaction.Transactional;

@Service
public class ServiceService {

	@Autowired
	private ServiceRepository serviceRepository;

	@Transactional
	public com.registrodevendasbackend.model.Service getServiceById(UUID id) {
		
		return serviceRepository.findById(id).get();
	}
	
	public ServiceRecordDTO getServiceByIdAsServiceRecordDTO(UUID id) throws ResourceNotAvailableException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user = new User();
		
		BeanUtils.copyProperties(auth.getPrincipal(), user);

		com.registrodevendasbackend.model.Service service = new com.registrodevendasbackend.model.Service();
		
		if(user.getRoleId() == Role.ADMINISTRATOR_ID) {
			
			service = serviceRepository.findById(id).get();
			
		}else {
			
			service = serviceRepository.findByIdAndUserId(id, user.getId()).get();
			
		}
		
		UUID serviceId = service.getId();
		
		String title = service.getTitle();
		
		String description = service.getDescription();
		
		BigDecimal basePrice = service.getBasePrice();
		
		Timestamp createdAt = service.getCreatedAt();
		
		Timestamp updatedAt = service.getUpdatedAt();
		
		return new ServiceRecordDTO(serviceId, title, description, basePrice, null, null, false, createdAt, updatedAt);
	}
	
	public List<ServiceRecordDTO> getFilteredServices(String title, String description) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user = new User();
		
		BeanUtils.copyProperties(auth.getPrincipal(), user);
		
		List<com.registrodevendasbackend.model.Service> filteredServices = serviceRepository.findAll(ServiceSpecifications.hasTitle(title).and(ServiceSpecifications.hasDescription(description)).and(ServiceSpecifications.belongsToUserId(user.getId())));

		List<ServiceRecordDTO> filteredServicesRecordList = new ArrayList<ServiceRecordDTO>();

		if(!filteredServices.isEmpty()) {
			
			for(com.registrodevendasbackend.model.Service service: filteredServices) {
				
				UUID serviceId = service.getId();
				
				String serviceTitle = service.getTitle();
				
				String serviceDescription = service.getDescription();
				
				BigDecimal basePrice = service.getBasePrice();
				
				Timestamp createdAt = service.getCreatedAt();
				
				Timestamp updatedAt = service.getUpdatedAt();
				
				ServiceRecordDTO serviceRecord = new ServiceRecordDTO(serviceId, serviceTitle, serviceDescription, basePrice, null, null, false, createdAt, updatedAt);
				
				filteredServicesRecordList.add(serviceRecord);
			}
		}else {
			
			throw new NoSuchElementException("Serviço não encontrado com os parâmetros pesquisados.");
		}
		
		return filteredServicesRecordList;
	}
	
	public ServiceRepository getServiceRepository() {
		return serviceRepository;
	}

	public void setServiceRepository(ServiceRepository serviceRepository) {
		this.serviceRepository = serviceRepository;
	}
}
