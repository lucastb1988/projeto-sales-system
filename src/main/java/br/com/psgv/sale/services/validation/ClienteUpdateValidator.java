package br.com.psgv.sale.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.psgv.sale.domain.Cliente;
import br.com.psgv.sale.dto.ClienteDTO;
import br.com.psgv.sale.repositories.ClienteRepository;
import br.com.psgv.sale.resources.exception.FieldMessage;

//Validação personalizada de cpf ou cnpj para Cliente
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	//HttpServletRequest para recuperar a request realizada na requisição ao servidor (/clientes/(chave id / valor 2))
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository repo;

	@Override
	public void initialize(ClienteUpdate ann) {
	}

	//esta validação será conjunta a anotação @Valid inserida no rest quando salvar ou atualizar um cliente
	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> errors = new ArrayList<>();
		
		//criada map para recuperar Id do parametro da requisição (/clientes/(chave id / valor 2))
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id")); //recuperar id do map encontrada url da requisição (/clientes/(chave id / valor 2))
		
		//Consulta a e-mail criada no ClienteRepository
		Cliente aux = repo.findByEmail(objDto.getEmail());
		//se for o mesmo id do cliente que está sendo atualizado pode deixar passar a validação, só da pau se for um id diferente do id a ser atualizado
		if (aux != null && !aux.getId().equals(uriId)) { //caso encontrar um e-mail quer dizer que já existe esse e-mail então dá erro
			errors.add(new FieldMessage("email", "Email já existente."));
		}
		
		//inserindo cada erro encontrado acima nas validações no context dentro do for
		for (FieldMessage e : errors) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return errors.isEmpty();
	}
}
