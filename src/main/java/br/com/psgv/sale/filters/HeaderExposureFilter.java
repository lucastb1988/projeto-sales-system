package br.com.psgv.sale.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

//Classe para expor explicitamente no header o parametro Location 
//(se não expor explicitamente o Angular não consegue pegar o id quando cria-se um novo objeto)
//no backend quando salva um objeto automaticamente enviamos para resposta do json o atributo location com o id novo preenchido
@Component
public class HeaderExposureFilter implements Filter {

	@Override
	public void destroy() {
	}

	//incluir no header do response o location com o id novo criado
	//Filtro que irá interceptar todas as requisições e irá expor o header location na resposta e encaminha a requisição para o ciclo normal
	//Dessa forma o angular irá conseguir ler esse cabeçalho
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		//Faz o casting do response para HttpServletResponse pois o metodo addHeader não tem no ServletResponse
		HttpServletResponse res = (HttpServletResponse) response;
		
		res.addHeader("access-control-expose-headers", "location");
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
	
	

}
