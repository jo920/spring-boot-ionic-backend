package com.jh.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jh.cursomc.domain.Cliente;
import com.jh.cursomc.repositories.ClienteRepository;
import com.jh.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired // essa annotation é utilizada para instaciar qualquer dependencia pelo Spring
	private ClienteRepository repo;
	
	
	public Cliente buscar(Integer id) {
		 Optional<Cliente> obj = repo.findById(id);		 
		 return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
}
