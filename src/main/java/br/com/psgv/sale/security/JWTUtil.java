package br.com.psgv.sale.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	//método de gerar token JWT
	public String generateToken(String userName) {
		return Jwts.builder() //Jwts.builder() é quem gera o token
				.setSubject(userName) //setando sujeito no qual quer ser gerado token
				.setExpiration(new Date(System.currentTimeMillis() + expiration)) //setando tempo de expiração
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()) //setando algoritmo + segredo da chave do token(que irá embaralhar o token)
				.compact();
	}
}
