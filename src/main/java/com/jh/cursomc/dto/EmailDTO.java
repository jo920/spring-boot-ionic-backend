package com.jh.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty; // vou verificar pq esta assim



public class EmailDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message =" O preenchimento do campo email é obrigatório!")
	@Email(message = "O email informado, está inválido")
	private String email;
	
	
	public EmailDTO() {
		
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
	
}
