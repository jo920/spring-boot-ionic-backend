package com.jh.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.jh.cursomc.services.DBService;
import com.jh.cursomc.services.EmailService;
import com.jh.cursomc.services.SmtpEmailService;

@Configuration // Estou dizendo que essa classe é para configuração do projeto
@Profile("dev") // Estou especificadno que todos os Bean que esta na classe é referente ao dev que criei no aplication.
public class DevConfig {

	@Autowired
    private	DBService dbservice;
	
	
	@Value("${spring.jpa.hibernate.ddl-auto}")// estou pegando o valor da aplication-dev.properties para validação
	private String strategy;
	
	
	@Bean
	public boolean instatiateDataBase() throws ParseException {
		
		if(!"create".equals(strategy)) {
			
			return false; // Se na aplication-dev.properties estiver diferente de create, nao executarei nada. 
			
		}
		dbservice.instantiateTestDatabase();
		
		return true;
	}
	
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
	
	
}
