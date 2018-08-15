package br.com.psgv.sale.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.psgv.sale.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	//método para preencher a data de vencimento do Pagamento se for boleto com 7 dias para vencimento
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		
		pagto.setDataVencimento(cal.getTime());
	}
}
