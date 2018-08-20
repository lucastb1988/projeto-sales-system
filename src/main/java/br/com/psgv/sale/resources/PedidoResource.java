package br.com.psgv.sale.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.psgv.sale.domain.Pedido;
import br.com.psgv.sale.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {
    
    @Autowired
    private PedidoService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		Pedido obj = service.find(id);

		return ResponseEntity.ok().body(obj);
	}
	
	// Não terá retorno de entidade no ResponseEntity / traz resposta com corpo vazio
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Pedido obj) { // @RequestBody faz o objeto ser convertido em json automaticamente
		obj = service.insert(obj);
		
		// adicionar e converter na url de inserir o novo id que acabou de ser gerado após o save
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri(); 
		
		// gera response 201(created) junto a uri criada
		return ResponseEntity.created(uri).build();
	}
	
	//método criado chamando pedidos paginados e por cliente informado
    //cliente só recupera seus pedidos, se for outro cliente não recupera os pedidos de tal cliente e dá pau
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<Pedido>> findAllPerPageByClient(
			//@RequestParam similar ao @QueryParam (não é obrigatorio informar caso não tenha validação)
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "instante") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "DESC") String direction) {
		
		Page<Pedido> list = service.findAllPerPageByClient(page, linesPerPage, orderBy, direction);
		
		return ResponseEntity.ok().body(list);
	}
}
