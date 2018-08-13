package br.com.psgv.sale.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.psgv.sale.domain.Categoria;
import br.com.psgv.sale.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
    
    @Autowired
    private CategoriaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria obj = service.find(id);

		//gera response 200(ok)
		return ResponseEntity.ok().body(obj);
	}
	
	// Não terá retorno de entidade no ResponseEntity / traz resposta com corpo vazio
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj) { //@RequestBody faz o objeto ser convertido em json automaticamente
		obj = service.insert(obj);
		// adicionar e converter na url de inserir o novo id que acabou de ser gerado após o save
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri(); //recupera o id criado e transforma em uri
		
		//gera response 201(created) junto a uri criada
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id) {
		obj.setId(id);
		obj = service.update(obj);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
