package br.com.psgv.sale.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemPedido implements Serializable {
	
	//tudo que é get é serializado no json e informado na resposta da requisição
	//getSubTotal será serializado no json na resposta da requisição
	
	private static final long serialVersionUID = 1L;
	
	//não pode fazer referência ciclica do item pedido para pedido e produto
	@JsonIgnore //não será serializado id
	@EmbeddedId //id embutido em um tipo auxiliar
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
	
	public double getSubTotal() {
		return (preco - desconto) * quantidade;
	}
	
	//tudo que comeca com get pode ser serializado
	//não pode fazer referência ciclica do item pedido para pedido
	@JsonIgnore
	public Pedido getPedido() {
		return id.getPedido();
	}
	
	//criado este método para que o ItemPedido seja capaz de definir o Pedido que está associado a ele
	public void setPedido(Pedido pedido) {
		id.setPedido(pedido);
	}
	
	public Produto getProduto() {
		return id.getProduto();
	}
	
	//criado este método para que o ItemPedido seja capaz de definir o Produto que está associado a ele
	public void setProduto(Produto produto) {
		id.setProduto(produto);
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
	
	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		StringBuilder builder = new StringBuilder();
		builder.append(getProduto().getNome());
		builder.append(", Qte: ");
		builder.append(getQuantidade());
		builder.append(", Preço unitário: ");
		builder.append(nf.format(getPreco()));
		builder.append(", Subtotal: ");
		builder.append(nf.format(getSubTotal()));
		builder.append("\n");
		return builder.toString();
	}
}
