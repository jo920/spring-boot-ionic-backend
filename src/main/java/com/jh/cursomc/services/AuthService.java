package com.jh.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jh.cursomc.domain.Cliente;
import com.jh.cursomc.repositories.ClienteRepository;
import com.jh.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private EmailService emailservice;

	private Random rand = new Random();// Gerador de valores aleatorios

	public void sendNewPassword(String email) {

		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente == null) {
			throw new ObjectNotFoundException("Email nao encontrado");
		}

		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));

		clienteRepository.save(cliente);

		emailservice.sendNewPasswordEmail(cliente, newPass);

	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {

			vet[i] = randomChar();
		}

		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);

		if (opt == 0) {// gera um digito

			return (char) (rand.nextInt(10) + 48);

		} else if (opt == 1) {// gera letra maiuscula

			return (char) (rand.nextInt(26) + 65);

		} else {// gera letra minuscula

			return (char) (rand.nextInt(26) + 97);

		}

	}

}
