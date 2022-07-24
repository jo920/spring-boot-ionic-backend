package com.jh.cursomc.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JWTUtil jwtutil;
	
	private UserDetailsService userDetailsService;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtutil,UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtutil = jwtutil;
		this.userDetailsService = userDetailsService;
	}

	protected void doFilterInternal(HttpServletRequest request,
			                       HttpServletResponse response,
			                       FilterChain chain) throws IOException, ServletException{
		
		String header = request.getHeader("Authorization"); // estou pegando o valor de autorização
		
		if(header != null && header.startsWith("Bearer ")) {
			
			//pegando o token e a autorização			
			UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7)); 
			if(auth != null) {// caso nao traga nulo eu libero a autorizacao
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		chain.doFilter(request,response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		if(jwtutil.tokenValido(token)) {
			String username = jwtutil.getUsername(token);
			UserDetails user = userDetailsService.loadUserByUsername(username);
			return new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
		}
		return null ;
	}
	
	
	
}
