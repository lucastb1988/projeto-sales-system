package br.com.psgv.sale.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment env;
	
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**"
	};
	
	//vetor para permitir somente consultar os dados (não permite edição, inserção ou exclusão)
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/categorias/**",
			"/clientes/**"
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//recuperando profiles ativos no projeto (test no application.properties)
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			//se no projeto existir profile test desabilita o cabeçalho para logar no h2-console (pois ao acessar o h2-console é necessário informar outras informações além de simplesmente só o h2-console)
			http.headers().frameOptions().disable();
		}
		
		http.cors().and().csrf().disable();//desabilita csrf que é um ataque em sessão que pode ser realizado em nossa aplicação (como nossa aplicação é stateless não é necessario esta segurança))
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()//permite somente endpoints com GET e informados no vetor
		.antMatchers(PUBLIC_MATCHERS).permitAll()//todos os caminhos que estiverem no vetor serão permitidos pelo Spring Security
		.anyRequest().authenticated();//para qualquer outra requisição exige autenticação
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//assegura que o backend não irá criar sessão de usuário
	}
	
	//Permitindo o acesso aos endpoints de multiplas fontes com as configurações básicas
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	//encripta senha do usuário
	//Componente para poder injetar em qql sistema esse encrypt de senha
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
