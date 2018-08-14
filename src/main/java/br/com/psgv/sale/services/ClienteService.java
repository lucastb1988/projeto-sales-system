package br.com.psgv.sale.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.psgv.sale.domain.Cliente;
import br.com.psgv.sale.dto.ClienteDTO;
import br.com.psgv.sale.exceptions.DataIntegrityException;
import br.com.psgv.sale.exceptions.ObjectNotFoundException;
import br.com.psgv.sale.repositories.ClienteRepository;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository repo;
    
    public Cliente find(Integer id) {
        Optional<Cliente> Cliente = repo.findById(id);
        return Cliente.orElseThrow(() -> new ObjectNotFoundException(
        		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }
    
    public Cliente insert(Cliente obj) {
    	obj.setId(null);
    	return repo.save(obj);
    }
    
    public Cliente update(Cliente obj) {
    	Cliente newObj = find(obj.getId());
    	updateData(newObj, obj); //atualizar somente os campos informados (senão todo objeto fica nulo)
    	return repo.save(newObj);
    }
    
    //atualizar somente os campos informados (senão todo objeto fica nulo)
    private void updateData(Cliente newObj, Cliente obj) {
    	newObj.setNome(obj.getNome());
    	newObj.setEmail(obj.getEmail());
    }
    
    public void delete(Integer id) {
    	find(id);
    	
    	try {
    		repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir pois existe entidades relacionadas.");
		}
    }
    
    public List<Cliente> findAll() {
    	return repo.findAll();
    }
    
    public Page<Cliente> findAllPerPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
    	PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
    	return repo.findAll(pageRequest);
    }
    
    public Cliente fromDto(ClienteDTO objDto) {
    	return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
    }
}
