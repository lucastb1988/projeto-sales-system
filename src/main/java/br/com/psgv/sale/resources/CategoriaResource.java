package br.com.psgv.sale.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.psgv.sale.domain.Categoria;
import br.com.psgv.sale.dto.CategoriaDTO;
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
	
	//somente autoriza usuário a acessar este endpoint se for admin (mesmo autenticado não funciona)
	//somente loga se for admin e passar token
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST) //Não terá retorno de entidade no ResponseEntity / traz resposta com corpo vazio
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto) { //@RequestBody faz o objeto ser convertido em json automaticamente
		Categoria obj = service.fromDto(objDto);
		obj = service.insert(obj);
		
		//adicionar e converter na url de inserir o novo id que acabou de ser gerado após o save
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri(); //recupera o id criado e transforma em uri
		
		//gera response 201(created) junto a uri criada
		return ResponseEntity.created(uri).build();
	}
	
	//somente autoriza usuário a acessar este endpoint se for admin (mesmo autenticado não funciona)
	//somente loga se for admin e passar token
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id) {
		Categoria obj = service.fromDto(objDto);
		obj.setId(id);
		obj = service.update(obj);
		
		//gera response 204(no content)
		return ResponseEntity.noContent().build();
	}
	
	//somente autoriza usuário a acessar este endpoint se for admin (mesmo autenticado não funciona)
	//somente loga se for admin e passar token
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> list = service.findAll();
		
		//map vai efetuar uma operação para cada elemento da lista utilizando java8
		//para transformar em listDto no final da função inserir o list no collect(Collectors.toList())
		List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findAllPerPage(
			//@RequestParam similar ao @QueryParam (não é obrigatorio informar caso não tenha validação)
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		
		Page<Categoria> list = service.findAllPerPage(page, linesPerPage, orderBy, direction);
		
		//Page já é java8 compliance então não é necessario passar o stream e collect(Collectors.toList()) como foi feito no serviço findAll acima
		Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));
		
		return ResponseEntity.ok().body(listDto);
	}
}
