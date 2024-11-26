package com.registrodevendasbackend.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.MethodNotAllowedException;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.registrodevendasbackend.DTO.AuthenticationDTO;
import com.registrodevendasbackend.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	TokenService tokenService;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		var token = recoverToken(request);
		
		if(token != null) {
			
			try {
				
				var login = tokenService.validateToken(token);
				
				UserDetails user = userRepository.findByEmail(login);
				
				var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				filterChain.doFilter(request, response);
			}catch(TokenExpiredException e) {
				
				SecurityContextHolder.getContext().setAuthentication(null);
				
				e.printStackTrace();
				
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.getWriter().println("{\"message\": \"Token Expirado.\"}");
			}
		}
	}
	
	private String recoverToken(HttpServletRequest request) {
		
		String authorizationHeader = request.getHeader("Authorization");
		
		if(authorizationHeader == null) {
			
			return null;
		}
		
		return authorizationHeader.replace("Bearer ", "");
	}

}
