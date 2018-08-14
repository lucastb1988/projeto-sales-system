package br.com.psgv.sale.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.psgv.sale.domain.enums.TipoCliente;
import br.com.psgv.sale.dto.ClienteNewDTO;
import br.com.psgv.sale.resources.exception.FieldMessage;
import br.com.psgv.sale.services.validation.utils.BR;

//Validação personalizada de cpf ou cnpj para Cliente
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Override
	public void initialize(ClienteInsert ann) {
	}

	//esta validação será conjunta a anotação @Valid inserida no rest quando salvar ou atualizar um cliente
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> errors = new ArrayList<>();
		
		if (TipoCliente.PESSOA_FISICA.getCodigo().equals(objDto.getTipo())
				&& !BR.isValidCpf(objDto.getCpfOuCnpj())) {
			errors.add(new FieldMessage("cpfOuCnpj", "CPF inválido."));
		}
		
		if (TipoCliente.PESSOA_JURIDICA.getCodigo().equals(objDto.getTipo())
				&& !BR.isValidCnpj(objDto.getCpfOuCnpj())) {
			errors.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido."));
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
