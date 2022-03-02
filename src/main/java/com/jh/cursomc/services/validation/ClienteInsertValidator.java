package com.jh.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.jh.cursomc.domain.Cliente;
import com.jh.cursomc.domain.enums.TipoCliente;
import com.jh.cursomc.dto.ClienteNewDTO;
import com.jh.cursomc.repositories.ClienteRepository;
import com.jh.cursomc.resources.exception.FieldMessage;
import com.jh.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	 public void initialize(ClienteInsert ann) {
	 }
	 @Override
	 public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
	 List<FieldMessage> list = new ArrayList<>();
	 
	 // inclua os testes aqui, inserindo erros na lista
	 
	 if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod())&& !BR.isValidCPF(objDto.getCpfOuCnpj())) {
		 list.add(new FieldMessage("cpfOuCnpj","CPF está invalido, por favor verificar!"));
	 }
	 
	 if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod())&& !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
		 list.add(new FieldMessage("cpfOuCnpj","CNPJ está invalido, por favor verificar!"));
	 }
	 
	Cliente aux = repo.findByEmail(objDto.getEmail());
	if(aux != null) {
		
		list.add(new FieldMessage("email","O email informado já existe!"));
	}
	 
	 for (FieldMessage e : list) {
	 context.disableDefaultConstraintViolation();
	 context.buildConstraintViolationWithTemplate(e.getMessage())
	 .addPropertyNode(e.getFieldName()).addConstraintViolation();
	 }
	 return list.isEmpty();
	 }
	}
