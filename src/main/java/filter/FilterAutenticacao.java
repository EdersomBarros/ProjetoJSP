package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import connection.SingleConnectionBanco;
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

@WebFilter(urlPatterns = { "/principal/*" }) /* intercepta todas as requisições que vierem do projeto ou mapeamento */
public class FilterAutenticacao extends HttpFilter implements Filter {

	private static Connection connection;

	public FilterAutenticacao() {
	}

	/* encerra os processos quando o servidor é parado */
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* Intercepta as requisições e as respostas no sistema */
	/* tudo que fizer no sistema vai passar por aqui */
	/* Ex.: validações de autenticações */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
			try {

				HttpServletRequest req = (HttpServletRequest) request;
				HttpSession session = req.getSession();
	
				String usuarioLogado = (String) session.getAttribute("usuario");
	
				String urlParaAutenticar = req.getServletPath();/* url está sendo acessada */
	
				/* validar se está logado senão redirediciona a tela de login */
				if (usuarioLogado == null
						&& !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {/* não está logado */
	
					RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
					request.setAttribute("msg", "Por favor realize o login!");
					redireciona.forward(request, response);
					return;/* Parar a execução a redireciona para o login */
	
				} else {
	
					chain.doFilter(request, response);
				}
	
				connection.commit();/*deu tudo certo então commita as alterações no banco*/

		} catch (Exception e) {
			e.printStackTrace();
			
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	/* Inicia os processos ou recursos qundo o servidor sobe o projeto */
	public void init(FilterConfig fConfig) throws ServletException {

		connection = SingleConnectionBanco.getConnection();
	}

}
