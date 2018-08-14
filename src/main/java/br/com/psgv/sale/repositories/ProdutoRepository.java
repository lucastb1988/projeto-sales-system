package br.com.psgv.sale.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.psgv.sale.domain.Categoria;
import br.com.psgv.sale.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	
	//a mesma consulta por escrita
	@Transactional(readOnly = true)
	@Query("SELECT DISTINCT obj "
			+ "FROM Produto obj "
			+ "INNER JOIN obj.categorias cat "
			+ "WHERE obj.nome LIKE %:nome% "
			+ "AND cat IN :categorias")
	public Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);

	//a mesma consulta com keyword
	//nome e categorias necessitam estar iguais na classe Produto
	//public Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);
}
