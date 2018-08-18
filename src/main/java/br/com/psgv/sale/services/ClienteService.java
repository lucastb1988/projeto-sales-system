package br.com.psgv.sale.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.psgv.sale.domain.Cidade;
import br.com.psgv.sale.domain.Cliente;
import br.com.psgv.sale.domain.Endereco;
import br.com.psgv.sale.domain.enums.Perfil;
import br.com.psgv.sale.domain.enums.TipoCliente;
import br.com.psgv.sale.dto.ClienteDTO;
import br.com.psgv.sale.dto.ClienteNewDTO;
import br.com.psgv.sale.exceptions.AuthorizationException;
import br.com.psgv.sale.exceptions.DataIntegrityException;
import br.com.psgv.sale.exceptions.ObjectNotFoundException;
import br.com.psgv.sale.repositories.ClienteRepository;
import br.com.psgv.sale.repositories.EnderecoRepository;
import br.com.psgv.sale.security.UserSpringSecurity;

@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private ClienteRepository repo;
    
    @Autowired
    private EnderecoRepository enderecoRepository;
    
    @Autowired
    private S3Service s3Service;
    
    public Cliente find(Integer id) {
    	
    	//se o cliente logado não for ADMIN e não for o cliente do id solicitado, lança uma exceção
    	UserSpringSecurity user = UserService.authenticated();
    	if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
    		throw new AuthorizationException("Acesso negado");
    	}
    	
        Optional<Cliente> Cliente = repo.findById(id);
        return Cliente.orElseThrow(() -> new ObjectNotFoundException(
        		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }
    
    @Transactional
    public Cliente insert(Cliente obj) {
    	obj.setId(null);
    	obj = repo.save(obj);
    	enderecoRepository.saveAll(obj.getEnderecos()); //salvar enderecos dentro de cliente (foi adicionado o endereco em cliente no fromDto)
    	return obj;
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
			throw new DataIntegrityException("Não é possivel excluir pois existe pedidos relacionados.");
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
    	return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
    }
    
    public Cliente fromDto(ClienteNewDTO objDto) {
    	Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), 
    			TipoCliente.toEnum(objDto.getTipo()), passwordEncoder.encode(objDto.getSenha())); //encodar com algoritmo Bcrypt a senha
    	Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
    	Endereco end = new Endereco(
    			null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
    	
    	cli.getEnderecos().add(end);
    	cli.getTelefones().add(objDto.getTelefone1());
    	if (objDto.getTelefone2() != null) {
    		cli.getTelefones().add(objDto.getTelefone2());
    	}
    	if (objDto.getTelefone3() != null) {
    		cli.getTelefones().add(objDto.getTelefone3());
    	}
    	
    	return cli;
    }
    
    public URI uploadProfilePicture(MultipartFile multipartFile) {
    	return s3Service.uploadFile(multipartFile);
    }
}
