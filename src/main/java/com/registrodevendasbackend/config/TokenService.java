package com.registrodevendasbackend.config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.registrodevendasbackend.model.User;

@Service
public class TokenService {

	@Value("${api.security.token.secret}")
	private String secret;
	
	public String generateToken(User user) {
		
		try {
			
			Algorithm algorithm = Algorithm.HMAC256(secret);
			
			String token = JWT.create()
					.withIssuer("registro-de-vendas")
					.withSubject(user.getUsername())
					.withExpiresAt(getExpirationDate())
					.sign(algorithm);
			
			return token;
		}catch(JWTCreationException e) {
			
			throw new RuntimeException("Erro na geração do token de autenticação", e);
		}
	}
	
	public String validateToken(String token) {
		
		try {
			
			Algorithm algorithm = Algorithm.HMAC256(secret);
			
			return JWT.require(algorithm)
					.withIssuer("registro-de-vendas")
					.build()
					.verify(token)
					.getSubject();
		}catch(JWTVerificationException e) {
			
			return "";
		}
	}
	
	private Instant getExpirationDate() {
		
		return LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.of("-03:00"));
	}
}
