package br.com.psgv.sale.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

import br.com.psgv.sale.domain.enums.EstadoPagamento;

//anotação para informar que o objeto Pagamento terá um campo a mais que necessitará ser informado a subClasse PagamentoComBoleto ou PagamentoComCartão
//ex: @type : "pagamentoComBoleto"
@Entity
@JsonTypeName("pagamentoComCartao") //informar qual o nome do novo campo que será serializado no objeto Pagamento no json de requisição
public class PagamentoComCartao extends Pagamento {
	
	private static final long serialVersionUID = 1L;

	private Integer numeroParcelas;
	
	public PagamentoComCartao() {
		
	}

	public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroParcelas) {
		super(id, estado, pedido);
		this.numeroParcelas = numeroParcelas;
	}

	public Integer getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(Integer numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}
}
