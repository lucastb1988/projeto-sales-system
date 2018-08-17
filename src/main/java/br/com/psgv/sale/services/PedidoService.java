package br.com.psgv.sale.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.psgv.sale.domain.Cliente;
import br.com.psgv.sale.domain.ItemPedido;
import br.com.psgv.sale.domain.PagamentoComBoleto;
import br.com.psgv.sale.domain.Pedido;
import br.com.psgv.sale.domain.enums.EstadoPagamento;
import br.com.psgv.sale.exceptions.AuthorizationException;
import br.com.psgv.sale.exceptions.ObjectNotFoundException;
import br.com.psgv.sale.repositories.ItemPedidoRepository;
import br.com.psgv.sale.repositories.PagamentoRepository;
import br.com.psgv.sale.repositories.PedidoRepository;
import br.com.psgv.sale.security.UserSpringSecurity;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository repo;
    
    @Autowired
    private BoletoService boletoService;
    
    @Autowired
    private ProdutoService produtoService;
    
    @Autowired
	private ClienteService clienteService;
    
    @Autowired
    private PagamentoRepository pagamentoRepository;
    
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    
    /*@Autowired
    private EmailService emailService;*/
    
    public Pedido find(Integer id) {
        Optional<Pedido> pedido = repo.findById(id);
        return pedido.orElseThrow(() -> new ObjectNotFoundException(
        		"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }
    
    @Transactional
    public Pedido insert(Pedido obj) {
    	obj.setId(null);
    	obj.setInstante(new Date());
    	obj.setCliente(clienteService.find(obj.getCliente().getId()));
    	obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
    	obj.getPagamento().setPedido(obj); //setar Pagamento no Pedido 
    	//preencher data de vencimento 7 dias após instante do pedido caso pagamento for boleto
    	if (obj.getPagamento() instanceof PagamentoComBoleto) {
    		PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
    		boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
    	}
    	
    	obj = repo.save(obj); //salvar pedido
    	pagamentoRepository.save(obj.getPagamento()); //salvar pagamento no pedido
    	
    	for (ItemPedido item : obj.getItens()) {
    		item.setDesconto(0.0);
    		item.setProduto(produtoService.find(item.getProduto().getId())); 
    		item.setPreco(item.getProduto().getPreco()); //precisa pegar o preço do produto e setar no ItemPedido
    		item.setPedido(obj); //associar este Item Pedido com o Pedido a ser salvo
    	}
    	
    	itemPedidoRepository.saveAll(obj.getItens()); //salvar todos os itens de pedido encontrados
    	//emailService.sendOrderConfirmationHtmlEmail(obj);
    	System.out.println(obj);
    	
    	return obj;
    }
    
    //método criado chamando pedidos paginados e por cliente informado
    //cliente só recupera seus pedidos, se for outro cliente não recupera os pedidos de tal cliente e dá pau
    public Page<Pedido> findAllPerPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
    	
    	UserSpringSecurity user = UserService.authenticated();
    	if (user == null) {
    		throw new AuthorizationException("Acesso negado");
    	}
    	
    	PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
    	Cliente cliente = clienteService.find(user.getId());
    	
    	return repo.findByCliente(cliente, pageRequest);
    }
}
