package com.jh.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.jh.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanceDoPedido) {
	
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanceDoPedido);
		cal.add(Calendar.DAY_OF_MONTH,7);
		pagto.setDatadeVencimento(cal.getTime());
		
	}
	
}
