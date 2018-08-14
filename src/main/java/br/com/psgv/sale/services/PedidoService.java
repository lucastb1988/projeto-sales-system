package br.com.psgv.sale.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.psgv.sale.domain.Pedido;
import br.com.psgv.sale.exceptions.ObjectNotFoundException;
import br.com.psgv.sale.repositories.PedidoRepository;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository repo;
    
    public Pedido find(Integer id) {
        Optional<Pedido> pedido = repo.findById(id);
        return pedido.orElseThrow(() -> new ObjectNotFoundException(
        		"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }
}
