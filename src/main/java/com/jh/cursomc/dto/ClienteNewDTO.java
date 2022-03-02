package com.jh.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.jh.cursomc.services.validation.ClienteInsert;

import javax.validation.constraints.Email;

@ClienteInsert
public class ClienteNewDTO implements Serializable { // Criei essa classe para salvar todos os dados os dados do cliente em uma unica classe.

	private static final long serialVersionUID = 1L;

	@NotEmpty(message =" O preenchimento do campo nome é obrigatório!")
	@Length(min = 10,max=60, message = "O campo nome deve ter no minimo 10 caracteres e o maximo de 60 caracteres.")
	private String nome;
	
	@NotEmpty(message =" O preenchimento do campo Email é obrigatório!")
	@Email(message = "Email Invalido!")
	private String email;
	
	@NotEmpty(message =" O preenchimento do campo CPF é obrigatório!")
	
	private String cpfOuCnpj;
	
	
	private Integer tipo;

	@NotEmpty(message =" O preenchimento do campo Logradouro é obrigatório!")
	private String logradouro;
	
	@NotEmpty(message =" O preenchimento do campo Numero é obrigatório!")
	private String numero;
	
	private String complemento;
	
	@NotEmpty(message =" O preenchimento do campo Bairro é obrigatório!")
	private String bairro;
	
	@NotEmpty(message =" O preenchimento do campo CEP é obrigatório!")
	private String cep;

	
	@NotEmpty(message =" O preenchimento do campo Telefone é obrigatório!")
	private String telefone1;
	
	
	private String telefone2;
	private String telefone3;

	private Integer cidadeId;

	public ClienteNewDTO() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getTelefone3() {
		return telefone3;
	}

	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}

	public Integer getCidadeId() {
		return cidadeId;
	}

	public void setCidadeId(Integer cidadeId) {
		this.cidadeId = cidadeId;
	}
}
