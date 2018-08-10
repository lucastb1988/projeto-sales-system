package br.com.psgv.sale.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.psgv.sale.domain.Cliente;
import br.com.psgv.sale.exceptions.ObjectNotFoundException;
import br.com.psgv.sale.repositories.ClienteRepository;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository repo;
    
    public Cliente buscar(Integer id) {
        Optional<Cliente> Cliente = repo.findById(id);
        return Cliente.orElseThrow(() -> new ObjectNotFoundException(
        		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }
}
