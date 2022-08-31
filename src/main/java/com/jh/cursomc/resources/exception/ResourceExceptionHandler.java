package com.jh.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.jh.cursomc.services.exceptions.AuthorizationException;
import com.jh.cursomc.services.exceptions.DataIntegrityException;
import com.jh.cursomc.services.exceptions.FileException;
import com.jh.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class) // Criando uma excessao para ver se o objeto existe ou nao
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(),HttpStatus.NOT_FOUND.value(),"Não encontrado",e.getMessage(),request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(DataIntegrityException.class) // Excessao criada, caso tente deleta algo onde possui vinculo com
													// produto e etc.
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(),HttpStatus.BAD_REQUEST.value(),"Integridade de dados",e.getMessage(),request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class) // Erro de validação ao tentar criar uma categoria nula
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		ValidationError err = new ValidationError(System.currentTimeMillis(),HttpStatus.UNPROCESSABLE_ENTITY.value(),"Erro de validação",e.getMessage(),request.getRequestURI());
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}

	
	
	@ExceptionHandler(AuthorizationException.class) // Excessao de autorização de conteudos
	public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(),HttpStatus.FORBIDDEN.value(),"Acesso negado!",e.getMessage(),request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}
	
	
	
	
	@ExceptionHandler(FileException.class) // Tratamento de arquivo para o Amazon S3
	public ResponseEntity<StandardError> file(FileException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(),HttpStatus.BAD_REQUEST.value(),"Erro de arquivo",e.getMessage(),request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	

	@ExceptionHandler(AmazonServiceException.class) // Tratamento da Amazon Service
	public ResponseEntity<StandardError> amazonService(AmazonServiceException e, HttpServletRequest request) {

		HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
		StandardError err = new StandardError(System.currentTimeMillis(),code.value(),"Erro Amazon Service",e.getMessage(),request.getRequestURI());
		return ResponseEntity.status(code).body(err);
	}
	
	

	@ExceptionHandler(AmazonClientException.class) // Tratamento da Amazon Client
	public ResponseEntity<StandardError> amazonClient(AmazonClientException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(),HttpStatus.BAD_REQUEST.value(),"Erro Amazon Client",e.getMessage(),request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	
	@ExceptionHandler(AmazonS3Exception.class) // Tratamento da Amazon S3(Se a chave de acesso estiver errada)
	public ResponseEntity<StandardError> amazons3(AmazonS3Exception e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(),HttpStatus.BAD_REQUEST.value(),"Erro S3",e.getMessage(),request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
}
