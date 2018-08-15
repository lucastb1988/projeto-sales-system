package br.com.psgv.sale.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.psgv.sale.domain.PagamentoComBoleto;
import br.com.psgv.sale.domain.PagamentoComCartao;

//Com @Configuration informa que esta Classe será configurada no inicio da execução da aplicação
//Foi criada esta classe para informar que existira um novo campo no objeto Pagamento no qual será configurado como PagamentoComCartao ou PagamentoComBoleto 
@Configuration
public class JacksonConfig {
	
	//https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-without

	//@Bean irá informar que está anotação estará junto a anotação @Configuration
	//configuração abaixo é padrão do Spring, o que irá mudar será somente as subClasses informadas abaixo
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(PagamentoComCartao.class);
				objectMapper.registerSubtypes(PagamentoComBoleto.class);
				super.configure(objectMapper);
			};
		};

		return builder;
	}
}
