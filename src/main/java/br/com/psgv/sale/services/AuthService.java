package br.com.psgv.sale.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.psgv.sale.domain.Cliente;
import br.com.psgv.sale.exceptions.ObjectNotFoundException;
import br.com.psgv.sale.repositories.ClienteRepository;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	private Random random = new Random();

	public void sendNewPassword(String email) {
		
		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPassword = gerarNewPassword();
		cliente.setSenha(passwordEncoder.encode(newPassword));
		
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPassword);
	}

	//vai gerar uma senha com 10 digitos aleatórios
	private String gerarNewPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar(i);
		}
		
		return new String(vet);
	}

	//captura os numeros aleatórios utilizando site unicode-table.com/pt/
	private char randomChar(int i) {
		int opt = random.nextInt(3);
		if (opt == 0) { //gera um digito
			return (char) (random.nextInt(10) + 48); //0 = 48 na tabela do unicode-table.com/pt/
			//gera um numero entre (10) de 0 a 9 + 48(0)
		}
		if (opt == 1) { //gera letra maiuscula
			return (char) (random.nextInt(26) + 65); //A = 65 na tabela do unicode-table.com/pt/
			//gera uma letra entre (26) de A a Z + 65(A)
		}
		else { //gera letra minuscula
			return (char) (random.nextInt(26) + 97); //a = 97 na tabela do unicode-table.com/pt/
			//gera uma letra entre (26) de a a z + 97(a)
		}
	}
}
