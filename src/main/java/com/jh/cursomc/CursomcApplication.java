package com.jh.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jh.cursomc.domain.Categoria;
import com.jh.cursomc.domain.Cidade;
import com.jh.cursomc.domain.Cliente;
import com.jh.cursomc.domain.Endereco;
import com.jh.cursomc.domain.Estado;
import com.jh.cursomc.domain.ItemPedido;
import com.jh.cursomc.domain.Pagamento;
import com.jh.cursomc.domain.PagamentoComBoleto;
import com.jh.cursomc.domain.PagamentoComCartao;
import com.jh.cursomc.domain.Pedido;
import com.jh.cursomc.domain.Produto;
import com.jh.cursomc.domain.enums.EstadoPagamento;
import com.jh.cursomc.domain.enums.TipoCliente;
import com.jh.cursomc.repositories.CategoriaRepository;
import com.jh.cursomc.repositories.CidadeRepository;
import com.jh.cursomc.repositories.ClienteRepository;
import com.jh.cursomc.repositories.EnderecoRepository;
import com.jh.cursomc.repositories.EstadoRepository;
import com.jh.cursomc.repositories.ItemPedidoRepository;
import com.jh.cursomc.repositories.PagamentoRepository;
import com.jh.cursomc.repositories.PedidoRepository;
import com.jh.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itempedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	
	
	
	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null,"Escritório");
		Categoria cat3 = new Categoria(null,"Automobilismo");
		Categoria cat4 = new Categoria(null,"Decoração");
		Categoria cat5 = new Categoria(null,"Perfumes");
		Categoria cat6 = new Categoria(null,"Roupas e acessorios");
		Categoria cat7 = new Categoria(null,"Celulares e Notebooks");
		
		Produto p1 = new Produto(null,"Computador",2500.00);
		Produto p2 = new Produto(null,"Impressora",800.00);
		Produto p3 = new Produto(null,"Mouse Gamer",100.00);
	
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		
		
		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia",est1);
		Cidade c2 = new Cidade(null, "São Paulo",est2);
		Cidade c3 = new Cidade(null,"Campinas",est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est1.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2)); // estou salvando os estados e cidades que criei acima
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		
		
		Cliente cli1 = new Cliente(null, "Maria Silva ","maria.silva@gmail.com","452112643554",TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("989767522","975624568"));
		
		Endereco e1 = new Endereco(null,"Rua Flores","300","Apto 303","Jardim","02447195",cli1,c1);
		Endereco e2 = new Endereco(null,"Rua Flores","300","Apto 303","Jardim","02447195",cli1,c1);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		
		// Criando dados para pagamento e pedidos
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1,e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1,e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		
		ItemPedido ip1 = new ItemPedido(ped1,p1,0.00,1,2000.00);
		ItemPedido ip2 = new ItemPedido(ped1,p3,0.00,2,80.00);
		ItemPedido ip3 = new ItemPedido(ped2,p2,100.00,1,800.00);
	
	     ped1.getItens().addAll(Arrays.asList(ip1,ip2));
	     ped2.getItens().addAll(Arrays.asList(ip3));
	     
	     p1.getItens().addAll(Arrays.asList(ip1));
	     p2.getItens().addAll(Arrays.asList(ip3));
	     p3.getItens().addAll(Arrays.asList(ip2));
	     
	     itempedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
	     
	}

}
