package com.jh.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jh.cursomc.domain.Cliente;
import com.jh.cursomc.domain.ItemPedido;
import com.jh.cursomc.domain.PagamentoComBoleto;
import com.jh.cursomc.domain.Pedido;
import com.jh.cursomc.domain.enums.EstadoPagamento;
import com.jh.cursomc.repositories.ItemPedidoRepository;
import com.jh.cursomc.repositories.PagamentoRepository;
import com.jh.cursomc.repositories.PedidoRepository;
import com.jh.cursomc.security.UserSS;
import com.jh.cursomc.services.exceptions.AuthorizationException;
import com.jh.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired // essa annotation é utilizada para instaciar qualquer dependencia pelo Spring
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoservice;

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteservice;
	
	@Autowired
	private EmailService emailservice;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteservice.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);

		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoservice.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}

		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}

		itemPedidoRepository.saveAll(obj.getItens());
		emailservice.sendOrderConfirmationHtmlEmail(obj);
		return obj;
		
	}
	
	
	//Implementei essa restrição onde o cliente busca os seus pedidos
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente =  clienteservice.find(user.getId());
		return repo.findByCliente(cliente, pageRequest);		
	}
	

}
