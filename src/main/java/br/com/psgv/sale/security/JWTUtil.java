package br.com.psgv.sale.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
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
	
	//testar se token é válido
	public boolean tokenValido(String token) {
		Claims claims = getClaims(token); //Claims reinvidica usuário e tempo de expiração, fica armazenado que a pessoa é o usuario tal e o tempo de expiração é tal
		if (claims != null) {
			String username = claims.getSubject(); //recupera usuário
			Date expirationDate = claims.getExpiration(); //recupera data de expiração do usuário
			Date now = new Date(System.currentTimeMillis());
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}
	
	//recupera usuário passando token
	public String getUsername(String token) {
		Claims claims = getClaims(token); //Claims reinvidica usuário e tempo de expiração, fica armazenado que a pessoa é o usuario tal e o tempo de expiração é tal
		if (claims != null) {
			return claims.getSubject(); //recupera usuário
		}
		return null;
	}
	//recupera Claims com usuário e tempo de expiração
	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}
}
