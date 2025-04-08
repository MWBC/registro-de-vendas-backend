package com.registrodevendasbackend.specifications;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.registrodevendasbackend.model.Service;

public class ServiceSpecifications {

	public static Specification<Service> hasTitle(String title) {
		
		return (root, query, builder) ->
			
			title == null ? null : builder.like(builder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
	}
	
	public static Specification<Service> hasDescription(String description) {
		
		return (root, query, builder) -> 
		
			description == null ? null : builder.like(builder.lower(root.get("description")), "%" + description.toLowerCase() + "%");
	}
	
	public static Specification<Service> belongsToUserId(UUID userId) {
		
		return (root, query, builder) ->
		
			builder.equal(root.get("userId"), userId);
	}
}
