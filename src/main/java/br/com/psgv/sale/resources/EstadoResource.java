package br.com.psgv.sale.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.psgv.sale.domain.Cidade;
import br.com.psgv.sale.domain.Estado;
import br.com.psgv.sale.dto.CidadeDTO;
import br.com.psgv.sale.dto.EstadoDTO;
import br.com.psgv.sale.services.CidadeService;
import br.com.psgv.sale.services.EstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {
	
	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAllByNome() {
		List<Estado> list = estadoService.findAllByNome();
		
		List<EstadoDTO> listDto = list.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value = "/{estadoId}/cidades", method = RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findByEstado(@PathVariable Integer estadoId) {
		List<Cidade> list = cidadeService.findByEstado(estadoId);
		
		List<CidadeDTO> listDto = list.stream().map(x -> new CidadeDTO(x)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto);
	}
}
