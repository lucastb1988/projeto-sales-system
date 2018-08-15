package br.com.psgv.sale.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;

import br.com.psgv.sale.domain.enums.EstadoPagamento;

//anotação para informar que o objeto Pagamento terá um campo a mais que necessitará ser informado a subClasse PagamentoComBoleto ou PagamentoComCartão
//ex: @type : "pagamentoComBoleto"
@Entity
@JsonTypeName("pagamentoComBoleto") //informar qual o nome do novo campo que será serializado no objeto Pagamento no json de requisição
public class PagamentoComBoleto extends Pagamento {
	
	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataVencimento;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataPagamento;
	
	public PagamentoComBoleto() {
		
	}

	public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date dataVencimento, Date dataPagamento) {
		super(id, estado, pedido);
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
}
