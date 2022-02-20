package com.jh.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jh.cursomc.domain.Cliente;
import com.jh.cursomc.dto.ClienteDTO;
import com.jh.cursomc.repositories.ClienteRepository;
import com.jh.cursomc.services.exceptions.DataIntegrityException;
import com.jh.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired // essa annotation é utilizada para instaciar qualquer dependencia pelo Spring
	private ClienteRepository repo;
	
	
	public Cliente find(Integer id) {
		 Optional<Cliente> obj = repo.findById(id);		 
		 return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public Cliente update(Cliente obj) {
		Cliente newobj = find(obj.getId());
		updateData(newobj,obj);
		return repo.save(newobj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Nao é possivel excluir porque há entidades relacionadas");
		}

	}
	
	
	
	public List<Cliente>findAll(){
		return repo.findAll();
	}
	
	// Estou implementando um metodo de paginação, pois na hora de trazer muitos dados nao sobrecarregue 
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}


	public Cliente fromDTO(ClienteDTO objDto) {
         return new Cliente(objDto.getId(),objDto.getNome(),objDto.getEmail(),null,null);
	}
	
	
	private  void updateData(Cliente newobj,Cliente obj) {
		newobj.setNome(obj.getNome());
		newobj.setEmail(obj.getEmail());
	}
}
