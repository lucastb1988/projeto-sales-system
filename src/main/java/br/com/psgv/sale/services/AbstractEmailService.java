package br.com.psgv.sale.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import br.com.psgv.sale.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;

	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj); //prepara e-mail a ser enviado
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail()); //Destinatário do email
		sm.setFrom(sender); //Remetente
		sm.setSubject("Pedido Confirmado! Código: " + obj.getId()); //Assunto
		sm.setSentDate(new Date(System.currentTimeMillis())); //Data do envio do email baseada no servidor
		sm.setText(obj.toString()); //Pedido criado com toString()
		
		return sm;
	}
	
}
