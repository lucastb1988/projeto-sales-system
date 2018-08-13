package br.com.psgv.sale.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.psgv.sale.domain.Categoria;
import br.com.psgv.sale.exceptions.DataIntegrityException;
import br.com.psgv.sale.exceptions.ObjectNotFoundException;
import br.com.psgv.sale.repositories.CategoriaRepository;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository repo;
    
    public Categoria find(Integer id) {
        Optional<Categoria> categoria = repo.findById(id); // Optional pois este objeto pode vir nulo
        return categoria.orElseThrow(() -> new ObjectNotFoundException(
        		"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }
    
    public Categoria insert(Categoria obj) {
    	obj.setId(null);
    	return repo.save(obj);
    }
    
    public Categoria update(Categoria obj) {
    	find(obj.getId());
    	return repo.save(obj);
    }
    
    public void delete(Integer id) {
    	find(id);
    	
    	try {
    		repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos relacionados a ela");
		}
    }
}
