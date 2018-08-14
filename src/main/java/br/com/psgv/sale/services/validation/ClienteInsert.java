package br.com.psgv.sale.services.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

//Anotação Customizada para atender a validação de cpf ou cnpj da entidade Cliente
@Constraint(validatedBy = ClienteInsertValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ClienteInsert {
		
	 String message() default "Erro de validação";
	 
	 Class<?>[] groups() default {};
	 Class<? extends Payload>[] payload() default {};
}
