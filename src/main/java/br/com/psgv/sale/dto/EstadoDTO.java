package br.com.psgv.sale.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.psgv.sale.domain.Estado;

public class EstadoDTO implements Serializable {

	private static final long serialVersionUID = -9031487931907790020L;

	private Integer id;

	@NotEmpty(message = "Preenchimento Obrigatório")
	@Length(max = 20, message = "O tamanho deve ser até 20 caracteres")
	private String nome;

	public EstadoDTO() {
		super();
	}

	public EstadoDTO(Estado obj) {
		id = obj.getId();
		nome = obj.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
