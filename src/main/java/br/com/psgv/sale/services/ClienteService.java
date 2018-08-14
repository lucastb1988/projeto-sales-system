package br.com.psgv.sale.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.psgv.sale.domain.Cliente;
import br.com.psgv.sale.dto.ClienteDTO;
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
    	find(obj.getId());
    	return repo.save(obj);
    }
    
    public void delete(Integer id) {
    	find(id);
    	repo.deleteById(id);
    }
    
    public Cliente fromDto(ClienteDTO objDto) {
    	return new Cliente(objDto.getId(), objDto.getNome(), 
    			objDto.getEmail(), objDto.getCpfOuCnpj(), objDto.getTipo());
    }
    
    public List<Cliente> findAll() {
    	return repo.findAll();
    }
}
