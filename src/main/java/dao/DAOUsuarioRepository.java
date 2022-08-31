package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {

	private Connection connection;

	public DAOUsuarioRepository() {

		connection = SingleConnectionBanco.getConnection();
	}

	public ModelLogin gravarUsuario(ModelLogin objeto, Long usuarioLogado) throws Exception {

		if (objeto.isNovo()) {/* grava um novo */
			String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id) VALUES (?, ?, ?, ?, ?);";

			PreparedStatement preparedSQL = connection.prepareStatement(sql);
			preparedSQL.setString(1, objeto.getLogin());
			preparedSQL.setString(2, objeto.getSenha());
			preparedSQL.setString(3, objeto.getNome());
			preparedSQL.setString(4, objeto.getEmail());
			preparedSQL.setLong(5, usuarioLogado);

			preparedSQL.execute();
			connection.commit();
		} else {
			String sql = "UPDATE public.model_login SET login=?, senha=?, nome=?, email=? WHERE id = " + objeto.getId()
					+ ";";

			PreparedStatement prepareSQL = connection.prepareStatement(sql);
			prepareSQL.setString(1, objeto.getLogin());
			prepareSQL.setString(2, objeto.getSenha());
			prepareSQL.setString(3, objeto.getNome());
			prepareSQL.setString(4, objeto.getEmail());

			prepareSQL.executeUpdate();

			connection.commit();

		}
		return this.consultaUsuario(objeto.getLogin(), usuarioLogado);

	}

	public List<ModelLogin> consultaUsuarioList(String nome, Long usuarioLogado) throws Exception {

		List<ModelLogin> retorno = new ArrayList<>();

		String sql = "SELECT * FROM model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, usuarioLogado);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {/* percorrer as linhas de resultado de sql */
			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha"));

			retorno.add(modelLogin);

		}

		return retorno;

	}

	public ModelLogin consultaUsuario(String login, Long usuarioLogado) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login where upper(login) = upper('" + login + "') and useradmin is false and usuario_id = " + usuarioLogado;
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) /* se tiver resultado */ {

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));

		}

		return modelLogin;

	}
	public ModelLogin consultaUsuario(String login) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login where upper(login) = upper('" + login + "') and useradmin is false;";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) /* se tiver resultado */ {

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			

		}

		return modelLogin;

	}
	
	public ModelLogin consultaUsuarioLogado(String login) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login where upper(login) = upper('" + login + "');";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) /* se tiver resultado */ {

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));

		}

		return modelLogin;

	}


	public ModelLogin consultaUsuarioID(String id, Long usuarioLogado) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login where id = ? and useradmin is false and usuario_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		statement.setLong(2,usuarioLogado);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) /* se tiver resultado */ {

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));

		}

		return modelLogin;

	}

	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws Exception {

		List<ModelLogin> retorno = new ArrayList<>();

		String sql = "SELECT * FROM model_login WHERE useradmin is false and usuario_id = " + userLogado;

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {/* percorrer as linhas de resultado de sql */
			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha"));

			retorno.add(modelLogin);

		}

		return retorno;

	}

	public boolean validaLogin(String login) throws Exception {

		String sql = "SELECT count(1) > 0 as existe from model_login where upper(login) = upper ('" + login + "');";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		resultado.next();
		return resultado.getBoolean("existe");

	}

	public void deletarUser(String idUser) throws Exception {

		String sql = "DELETE FROM model_login WHERE id = ? and useradmin is false;";
		PreparedStatement preparedSQL = connection.prepareStatement(sql);
		preparedSQL.setLong(1, Long.parseLong(idUser));
		preparedSQL.executeUpdate();

		connection.commit();

	}

}
