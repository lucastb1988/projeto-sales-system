package br.com.psgv.sale.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.psgv.sale.services.DBService;
import br.com.psgv.sale.services.EmailService;
import br.com.psgv.sale.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;

	@Bean
	public boolean instantiateDataBase() throws ParseException {
		dbService.instantiateTestDataBase();
		return true;
	}

	// Criando este @Bean (Componente) é automaticado instanciado em todo código
	// quando este método for chamado na aplicação
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
