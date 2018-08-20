package br.com.psgv.sale.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.psgv.sale.dto.CredenciaisDTO;

//Classe que irá interceptar a requisição de Login e verificar se os dados informados estão corretos ou não
//esta classe UsernamePasswordAuthenticationFilter do Spring é quem irá realizar este trabalho
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
	
	private JWTUtil jwtUtil;
	
	//injentando as duas variaveis no construtor
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	//método para tentar autenticar
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			
			//operação para recuperar os dados que vieram na requisição e convertê-los em CredenciaisDTO
			//instanciando CredenciaisDTO a partir dos dados que vieram na requisição
			CredenciaisDTO creds = 
					new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);
			
			//instanciar um token do Spring Security recebendo os parametros de CredenciaisDTO
			UsernamePasswordAuthenticationToken authToken = 
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>());
					
			//método que realmente vai verificar se os parametros informados são válidos
			//authenticationManager verifica e utiliza os contratos informados para verificar se são válidos
			Authentication auth = authenticationManager.authenticate(authToken);
			
			//este objeto irá informar ao Spring Security se é válido ou não
			//se parametros estiverem corretos irá passar
			return auth;
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	//se autenticação der certa o que preciso fazer
	//necessita gerar um token e adicionar o mesmo na resposta da requisição
	//este método já recebe o objeto auth informado acima se o token foi válido ou não
	@Override
	protected void successfulAuthentication(HttpServletRequest request, 
											HttpServletResponse response, 
											FilterChain chain,
											Authentication auth) throws IOException, ServletException {
		
		//getPrincipal() irá retornar o usuário do Spring e com o Casting ele recupera o seu usuário informado
		String userName = ((UserSpringSecurity) auth.getPrincipal()).getUsername(); 
		//a partir desse usuário encontrado ele gera o token
		String token = jwtUtil.generateToken(userName);
		//gera a resposta no cabeçalho informando token
		response.addHeader("Authorization", "Bearer " + token);
		//Informa ao CORS(Cross-origin resource sharing) que o response do header estará sendo customizado e será incluido no header o campo Authorization
		response.addHeader("access-control-expose-headers", "Authorization");
	}
}
