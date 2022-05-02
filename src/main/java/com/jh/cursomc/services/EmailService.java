package com.jh.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.jh.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
}
