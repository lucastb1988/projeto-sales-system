package br.com.psgv.sale.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.psgv.sale.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	//Query realizada por keyword
	//Utilizando @Transactional faz a consulta ficar mais rápida e diminui Locking no gerenciamento de transações no BD
	@Transactional(readOnly = true)
	Cliente findByEmail(String email);
}
