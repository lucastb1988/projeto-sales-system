package br.com.psgv.sale;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.psgv.sale.domain.Categoria;
import br.com.psgv.sale.domain.Cidade;
import br.com.psgv.sale.domain.Cliente;
import br.com.psgv.sale.domain.Endereco;
import br.com.psgv.sale.domain.Estado;
import br.com.psgv.sale.domain.Produto;
import br.com.psgv.sale.domain.enums.TipoCliente;
import br.com.psgv.sale.repositories.CategoriaRepository;
import br.com.psgv.sale.repositories.CidadeRepository;
import br.com.psgv.sale.repositories.ClienteRepository;
import br.com.psgv.sale.repositories.EnderecoRepository;
import br.com.psgv.sale.repositories.EstadoRepository;
import br.com.psgv.sale.repositories.ProdutoRepository;

@SpringBootApplication
public class PsgvApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private EstadoRepository estadoRepository;
    
    @Autowired
    private CidadeRepository cidadeRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private EnderecoRepository enderecoRepository;

    public static void main(String[] args) {
        SpringApplication.run(PsgvApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");
        
        Produto p1 = new Produto(null, "Computador", 2000.00);
        Produto p2 = new Produto(null, "Impressora", 800.00);
        Produto p3 = new Produto(null, "Mouse", 80.00);
        
        cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
        cat2.getProdutos().addAll(Arrays.asList(p2));
        
        p1.getCategorias().addAll(Arrays.asList(cat1));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
        p3.getCategorias().addAll(Arrays.asList(cat1));
        
        categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
        
        Estado est1 = new Estado(null, "Minas Gerais");
        Estado est2 = new Estado(null, "São Paulo");
        
        Cidade c1 = new Cidade(null, "Uberlândia", est1);
        Cidade c2 = new Cidade(null, "São Paulo", est2);
        Cidade c3 = new Cidade(null, "Campinas", est2);
        
        est1.getCidades().addAll(Arrays.asList(c1));
        est2.getCidades().addAll(Arrays.asList(c2, c3));
        
        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
        
        Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "38941569885", TipoCliente.PESSOA_FISICA);
        
        cli1.getTelefones().addAll(Arrays.asList("23589546", "956321592"));
        
        Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 23", "Jardim", "09551070", cli1, c1);
        Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777102", cli1, c2);
        
        cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
        
        clienteRepository.saveAll(Arrays.asList(cli1));
        enderecoRepository.saveAll(Arrays.asList(e1, e2));
        
    }
}
