package com.jh.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.jh.cursomc.services.DBService;
import com.jh.cursomc.services.EmailService;
import com.jh.cursomc.services.MockEmailService;

@Configuration // Estou dizendo que essa classe é para configuração do projeto
@Profile("test") // Estou especificadno que todos os Bean que esta na classe é referente ao teste que criei no aplication.
public class TestConfig {

	@Autowired
    private	DBService dbservice;
	
	@Bean
	public boolean instatiateDataBase() throws ParseException {
		
		dbservice.instantiateTestDatabase();
		
		return true;
	}
	
	@Bean
	public EmailService emailservice() {
		
		return new MockEmailService();
	}	
}
