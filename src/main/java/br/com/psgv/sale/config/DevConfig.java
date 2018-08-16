package br.com.psgv.sale.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.psgv.sale.services.DBService;
import br.com.psgv.sale.services.EmailService;
import br.com.psgv.sale.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy; // captura a chave dentro de application.properties
	
	@Bean
	public boolean instantiateDataBase() throws ParseException {
		
		//se a chave spring.jpa.hibernate.ddl-auto dentro de application.properties 
		//for diferente de "create" não executa a chamada ao banco para criar todas as tabelas novamente 
		if (!"create".equals(strategy)) { 
			return false;
		}
		
		dbService.instantiateTestDataBase();
		
		return true;
	}
	
	//Criando este @Bean (Componente) é automaticado instanciado em todo código quando este método for chamado na aplicação
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
