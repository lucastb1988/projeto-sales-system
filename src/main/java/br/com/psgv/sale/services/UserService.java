package br.com.psgv.sale.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.psgv.sale.security.UserSpringSecurity;

@Service
public class UserService {
	
	//Static independente pode ser chamado independente de estar instanciado ou não
	//retorna o usuário que estiver logado no sistema
	public static UserSpringSecurity authenticated() {
		try {
			return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
}
