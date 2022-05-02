package com.jh.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.jh.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		
	SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj); 
	sendEmail(sm);
	}
	
	private SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado!! O codigo do seu Pedido é: "+ obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
	
	

}
