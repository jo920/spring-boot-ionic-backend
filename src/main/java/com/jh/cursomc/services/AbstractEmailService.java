package com.jh.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.jh.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {

		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	private SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {

		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado!! O codigo do seu Pedido é: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}

	// Criando metodos para retornar email em html
	// Data: 03/05/2022

	// Estou utilizando esse metodo para retornar o html com os dados do
	// pedido(utilizando o template que foi criado)
	protected String htmlFromTemplatePedido(Pedido obj) {

		Context context = new Context();
		context.setVariable("pedido", obj);

		// retorno o html que coloquei neste caminho
		return templateEngine.process("email/confirmacaoPedido", context);

	}

	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		try{MimeMessage mm = prepareMimeMessageFromPedido(obj);
		sendHtmlEmail(mm);

		}catch(MessagingException e) {
			sendOrderConfirmationEmail(obj);
		}
	}

	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Pedido confirmado!! O codigo do seu Pedido é: " + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(obj), true);
		return mimeMessage;
	}

}
