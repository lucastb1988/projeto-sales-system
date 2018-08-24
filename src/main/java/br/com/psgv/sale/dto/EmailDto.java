package br.com.psgv.sale.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class EmailDto implements Serializable {

	private static final long serialVersionUID = -9031487931907790020L;

	@NotEmpty(message = "Preenchimento Obrigatório")
	@Email(message = "E-mail inválido")
	private String email;

	public EmailDto() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
