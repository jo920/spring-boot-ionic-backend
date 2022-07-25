package com.jh.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.jh.cursomc.security.UserSS;

public class UserService {

	public static UserSS authenticated() {

		try {
			// metodo usado para retornar o usuario que esta logado no sistema
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		    } catch (Exception e) {

			return null;
		}

	}

}
