package br.com.psgv.sale.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.psgv.sale.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	/*@Autowired
	private JavaMailSender javaMailSender;*/

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
	
	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context();
		
		//dá o nome de pedido (alias) para corresponder ao html confirmacaoPedido.html e informa que obj será o valor a utilizar
		context.setVariable("pedido", obj); 
		
		//informa caminho do html para informar ao Thymeleaf (src/main/resources e templates é padrão do Spring)
		return templateEngine.process("email/confirmacaoPedido", context);
	}
	
	/*public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(obj); //prepara e-mail a ser enviado
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(obj); //protege a exceção enviando um e-mail com texto plano (toString) no lugar do email baseado em html
		}
	}
	
	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		MimeMessage mm = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mm, true);
		mmh.setTo(obj.getCliente().getEmail()); //Destinatário do email
		mmh.setFrom(sender); //Remetente
		mmh.setSubject("Pedido Confirmado! Código: " + obj.getId()); //Assunto
		mmh.setSentDate(new Date(System.currentTimeMillis())); //Data do envio do email baseada no servidor
		mmh.setText(htmlFromTemplatePedido(obj), true); //Passa o template criado em html para enviar ao cliente como texto
		
		return mm;
	}*/
}
