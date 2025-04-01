package com.registrodevendasbackend.exception;

public class ResourceNotAvailableException extends Exception {

	private static final long serialVersionUID = -2508367363391769698L;

	public ResourceNotAvailableException() {
		
		super();
	}
	
	public ResourceNotAvailableException(String message) {
		
		super(message);
	}
}
