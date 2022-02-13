package com.jh.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jh.cursomc.domain.Pedido;
import com.jh.cursomc.repositories.PedidoRepository;
import com.jh.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired // essa annotation é utilizada para instaciar qualquer dependencia pelo Spring
	private PedidoRepository repo;
	
	
	public Pedido find(Integer id) {
		 Optional<Pedido> obj = repo.findById(id);		 
		 return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
}
