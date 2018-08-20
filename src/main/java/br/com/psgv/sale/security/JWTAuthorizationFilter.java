package br.com.psgv.sale.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	private JWTUtil jwtUtil;
	
	private UserDetailsService userDetailsService;

	//este filtro irá analisar o token e ver se é válido e para isso também precisa do UserDetailsService para verificar as informaçoes do usuario
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, 
								  JWTUtil jwtUtil,
								  UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService; 
	}

	//método que intercepta a requisição e checar se este usuário está realmente autorizado
	//método padrão da classe que irá executar e ver se podemos continuar
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain chain) throws IOException, ServletException {
		
		//Procedimento para liberar o usuário que está tentando acessar o endpoint
		String header = request.getHeader("Authorization"); //recupera o valor do cabeçalho da requisição (token)
		if (header != null && header.startsWith("Bearer ")) {
			//recupera o token e joga nesta classe
			UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));
			if (auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth);//seta o token
			}
		}

		//Passado da validação irá continuar a realizar a requisição normalmente
		chain.doFilter(request, response);
	}

	//pegar autenticação vinda da requisição
	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		
		if (jwtUtil.tokenValido(token)) {
			String username = jwtUtil.getUsername(token);//recupera usuario passando token
			UserDetails user = userDetailsService.loadUserByUsername(username); //recupera usuario
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		
		return null;
	}
}
