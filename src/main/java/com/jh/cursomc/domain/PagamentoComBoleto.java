package com.jh.cursomc.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.cursomc.domain.enums.EstadoPagamento;
@Entity
public class PagamentoComBoleto extends Pagamento{
	
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern = "dd/MM/yyyy") // Estou formando a data para sair somente dia/mes e ano e a hora do pedido 
	private Date datadeVencimento;
	@JsonFormat(pattern = "dd/MM/yyyy") // Estou formando a data para sair somente dia/mes e ano e a hora do pedido 
	private Date dataPagamento;
	
	

	public PagamentoComBoleto() {
		
	}



	public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido,Date datadeVencimento, Date dataPagamento) {
		super(id, estado, pedido);
		this.datadeVencimento = datadeVencimento;
		this.dataPagamento = dataPagamento;
	}



	public Date getDatadeVencimento() {
		return datadeVencimento;
	}



	public void setDatadeVencimento(Date datadeVencimento) {
		this.datadeVencimento = datadeVencimento;
	}



	public Date getDataPagamento() {
		return dataPagamento;
	}



	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
	
}
