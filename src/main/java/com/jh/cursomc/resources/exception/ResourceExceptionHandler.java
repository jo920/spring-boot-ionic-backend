package com.jh.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jh.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	
	@ExceptionHandler(ObjectNotFoundException.class) // annotation para indicar para tratar essa excessao 
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e ,HttpServletRequest request){
		
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(),e.getMessage(),System.currentTimeMillis()); // Criando mensagem de erro quando aparece status de nao encontrado
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
		
	}
	
	
}
