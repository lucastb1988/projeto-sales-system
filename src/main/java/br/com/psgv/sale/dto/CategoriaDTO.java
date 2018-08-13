package br.com.psgv.sale.dto;

import java.io.Serializable;

import br.com.psgv.sale.domain.Categoria;

public class CategoriaDTO implements Serializable {
    
    private static final long serialVersionUID = -9031487931907790020L;

	private Integer id;
	private String nome;
	
	public CategoriaDTO() {
		super();
	}
	
	public CategoriaDTO(Categoria obj) {
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
