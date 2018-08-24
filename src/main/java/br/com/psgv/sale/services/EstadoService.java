package br.com.psgv.sale.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.psgv.sale.domain.Estado;
import br.com.psgv.sale.repositories.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repo;

	public List<Estado> findAllByNome() {
		return repo.findAllByOrderByNome();
	}
}
