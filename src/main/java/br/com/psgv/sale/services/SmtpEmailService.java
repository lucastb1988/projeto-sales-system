package br.com.psgv.sale.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import br.com.psgv.sale.domain.Pedido;

public class SmtpEmailService extends AbstractEmailService {
	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Autowired
	private MailSender mailSender;
	
	/*@Autowired
	private JavaMailSender javaMailSender;*/

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Enviando email...");
		mailSender.send(msg); //enviar para seu e-mail de verdade recuperando a mensagem
		LOG.info("Email enviado");
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
	}

	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
	}

	/*@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Enviando email HTML...");
		javaMailSender.send(msg); //enviar para seu e-mail de verdade recuperando a mensagem
		LOG.info("Email enviado");
	}*/

}
