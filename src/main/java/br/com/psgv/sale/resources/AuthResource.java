package br.com.psgv.sale.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.psgv.sale.dto.EmailDto;
import br.com.psgv.sale.security.JWTUtil;
import br.com.psgv.sale.security.UserSpringSecurity;
import br.com.psgv.sale.services.AuthService;
import br.com.psgv.sale.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService authService;

	//método para dar refresh no token / quando está para expirar o tempo este método serve 
	//para renovar o token caso token esteja válido
	//algumas aplicações como facebook utilizam este refresh para que não seja necessário logar com token toda hora
	//enpoint protegido por autenticação, se usuário não estiver logado não acessa este endpoint
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSpringSecurity user = UserService.authenticated(); //pega o usuario autenticado
		String token = jwtUtil.generateToken(user.getUsername()); //gera o token novo através do usuario logado
		response.addHeader("Authorization", "Bearer " + token); //adiciona na resposta do header o token novo
		//Informa ao CORS(Cross-origin resource sharing) que o response do header estará sendo customizado e será incluido no header o campo Authorization
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}
	
	//requisição para chamar esqueci a senha e enviar uma nova a um e-mail já existente
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDto objDto) {
		authService.sendNewPassword(objDto.getEmail());

		return ResponseEntity.noContent().build();
	}
	
}
