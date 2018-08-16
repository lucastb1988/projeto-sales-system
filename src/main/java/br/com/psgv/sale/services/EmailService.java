package br.com.psgv.sale.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.psgv.sale.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
