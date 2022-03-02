package com.jh.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jh.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Integer> {

	@Transactional(readOnly=true)// usei essa annotation fala que nao precisa ser envolvida como transação com o banco de dados
	Cliente findByEmail(String email);// Criei um metodo para verificar os email que sao salvos no banco
	

}
