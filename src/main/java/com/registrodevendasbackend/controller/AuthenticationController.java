package com.registrodevendasbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.registrodevendasbackend.DTO.AuthenticationDTO;
import com.registrodevendasbackend.DTO.LoginResponseDTO;
import com.registrodevendasbackend.config.TokenService;
import com.registrodevendasbackend.model.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
		
		var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password());
		
		try {
			
			var auth = authenticationManager.authenticate(usernamePassword);			
			
			var token = tokenService.generateToken((User) auth.getPrincipal());
			
			return ResponseEntity.ok(new LoginResponseDTO(token));
		}catch(BadCredentialsException e) {
			
			e.printStackTrace();
			
			return ResponseEntity.badRequest().body("Usuário inexistente ou senha inválida");
		}
	}
}
