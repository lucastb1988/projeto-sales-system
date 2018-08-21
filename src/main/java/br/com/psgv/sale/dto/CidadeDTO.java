package br.com.psgv.sale.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.psgv.sale.domain.Cidade;

public class CidadeDTO implements Serializable {
    
    private static final long serialVersionUID = -9031487931907790020L;

	private Integer id;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	@Length(max = 40, message = "O tamanho deve ser até 40 caracteres")
	private String nome;
	
	public CidadeDTO() {
		super();
	}
	
	public CidadeDTO(Cidade obj) {
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
