package filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/principal/*"}) /* intercepta todas as requisi��es que vierem do projeto ou mapeamento */
public class FilterAutenticacao extends HttpFilter implements Filter {

	public FilterAutenticacao() {
	}

	/* encerra os processos quando o servidor � parado */
	public void destroy() {
	}

	/* Intercepta as requisi��es e as respostas no sistema */
	/* tudo que fizer no sistema vai passar por aqui */
	/* Ex.: valida��es de autentica��es */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();

		String usuarioLogado = (String) session.getAttribute("usuario");

		String urlParaAutenticar = req.getServletPath();/* url est� sendo acessada */

		/* validar se est� logado sen�o redirediciona a tela de login */
		if (usuarioLogado == null &&
				!urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {/* n�o est� logado */

			RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
			request.setAttribute("msg", "Por favor realize o login!");
			redireciona.forward(request, response);
			return;/* Parar a execu��o a redireciona para o login */

		} else {

			chain.doFilter(request, response);
		}

	}

	/* Inicia os processos ou recursos qundo o servidor sobe o projeto */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
