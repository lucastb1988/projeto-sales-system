package br.com.psgv.sale.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.psgv.sale.domain.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
	
	//Query realizada por keyword
	// Utilizando @Transactional faz a consulta ficar mais rápida e diminui Locking
	// no gerenciamento de transações no BD
	@Transactional(readOnly = true)
	List<Estado> findAllByOrderByNome();
}
