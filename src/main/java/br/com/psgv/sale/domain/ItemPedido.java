package br.com.psgv.sale.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemPedido implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
<<<<<<< HEAD
	@EmbeddedId //anotação para informar que este id é embutido em um tipo auxiliar
=======
	//não pode fazer referência ciclica do item pedido para pedido e produto
	@JsonIgnore //não será serializado id
	@EmbeddedId //id embutido em um tipo auxiliar
>>>>>>> 5ed1c4f73a896e85f49b9e11ae2ac509d1b50aa3
	private ItemPedidoPk id = new ItemPedidoPk(); //informar que o id é uma chave composta de Pedido + Produto

	private Double desconto;
	private Integer quantidade;
	private Double preco;
	
	public ItemPedido() {
		super();
	}

	public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preco) {
		super();
		id.setPedido(pedido); //setar como id o Pedido que veio do ItemPedidoPk
		id.setProduto(produto); //setar como id o Produto que veio do ItemPedidoPk
		this.desconto = desconto;
		this.quantidade = quantidade;
		this.preco = preco;
	}
	
	//tudo que comeca com get pode ser serializado
	//não pode fazer referência ciclica do item pedido para pedido
	@JsonIgnore
	public Pedido getPedido() {
		return id.getPedido();
	}
	
	public Produto getProduto() {
		return id.getProduto();
	}

	public ItemPedidoPk getId() {
		return id;
	}

	public void setId(ItemPedidoPk id) {
		this.id = id;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
