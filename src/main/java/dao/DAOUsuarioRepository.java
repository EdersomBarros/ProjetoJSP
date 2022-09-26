package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
			String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

			PreparedStatement preparedSQL = connection.prepareStatement(sql);
			preparedSQL.setString(1, objeto.getLogin());
			preparedSQL.setString(2, objeto.getSenha());
			preparedSQL.setString(3, objeto.getNome());
			preparedSQL.setString(4, objeto.getEmail());
			preparedSQL.setLong(5, usuarioLogado);
			preparedSQL.setString(6, objeto.getPerfil());
			preparedSQL.setString(7, objeto.getSexo());
			preparedSQL.setString(8, objeto.getCep());
			preparedSQL.setString(9, objeto.getLogradouro());
			preparedSQL.setString(10, objeto.getBairro());
			preparedSQL.setString(11, objeto.getLocalidade());
			preparedSQL.setString(12, objeto.getUf());
			preparedSQL.setString(13, objeto.getNumero());


			preparedSQL.execute();
			connection.commit();

			if (objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {

				sql = "update model_login set fotouser =?, extensaofotouser=? where login=?";

				preparedSQL = connection.prepareStatement(sql);

				preparedSQL.setString(1, objeto.getFotouser());
				preparedSQL.setString(2, objeto.getExtensaofotouser());
				preparedSQL.setString(3, objeto.getLogin());

				preparedSQL.execute();
				connection.commit();
			}

		} else {
			String sql = "UPDATE public.model_login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro=?, localidade=?, uf=?, numero=? WHERE id = "
					+ objeto.getId() + ";";

			PreparedStatement prepareSQL = connection.prepareStatement(sql);
			prepareSQL.setString(1, objeto.getLogin());
			prepareSQL.setString(2, objeto.getSenha());
			prepareSQL.setString(3, objeto.getNome());
			prepareSQL.setString(4, objeto.getEmail());
			prepareSQL.setString(5, objeto.getPerfil());
			prepareSQL.setString(6, objeto.getSexo());
			prepareSQL.setString(7, objeto.getCep());
			prepareSQL.setString(8, objeto.getLogradouro());
			prepareSQL.setString(9, objeto.getBairro());
			prepareSQL.setString(10, objeto.getLocalidade());
			prepareSQL.setString(11, objeto.getUf());
			prepareSQL.setString(12, objeto.getNumero());

			prepareSQL.executeUpdate();

			connection.commit();

			if (objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {

				sql = "update model_login set fotouser =?, extensaofotouser=? where id=?";

				prepareSQL = connection.prepareStatement(sql);

				prepareSQL.setString(1, objeto.getFotouser());
				prepareSQL.setString(2, objeto.getExtensaofotouser());
				prepareSQL.setLong(3, objeto.getId());

				prepareSQL.execute();
				connection.commit();
			}

		}
		return this.consultaUsuario(objeto.getLogin(), usuarioLogado);

	}
	
	public int consultaUsuarioListTotalPaginaPaginacao(String nome, Long usuarioLogado) throws Exception {


		String sql = "SELECT count(1) as total from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? ";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, usuarioLogado);

		ResultSet resultado = statement.executeQuery();

		resultado.next();

		Double cadastros = resultado.getDouble("total");
		Double porPagina = 5.0;
		Double pagina = cadastros / porPagina;
		Double resto = pagina % 2;

		if (resto > 0) {
			pagina++;
		}
		return pagina.intValue();


	}

	public List<ModelLogin> consultaUsuarioList(String nome, Long usuarioLogado) throws Exception {

		List<ModelLogin> retorno = new ArrayList<>();

		String sql = "SELECT * FROM model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? limit 5";

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
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			// modelLogin.setSenha(resultado.getString("senha"));

			retorno.add(modelLogin);

		}

		return retorno;

	}
	public List<ModelLogin> consultaUsuarioListOffSet(String nome, Long usuarioLogado, int offSet) throws Exception {

		List<ModelLogin> retorno = new ArrayList<>();

		String sql = "SELECT * FROM model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? offset "+offSet+" limit 5";

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
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			// modelLogin.setSenha(resultado.getString("senha"));

			retorno.add(modelLogin);

		}

		return retorno;

	}

	public ModelLogin consultaUsuario(String login, Long usuarioLogado) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login where upper(login) = upper('" + login
				+ "') and useradmin is false and usuario_id = " + usuarioLogado;
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) /* se tiver resultado */ {

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("cep"));
			modelLogin.setNumero(resultado.getString("numero"));


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
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("cep"));
			modelLogin.setNumero(resultado.getString("numero"));
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
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("cep"));
			modelLogin.setNumero(resultado.getString("numero"));

		}

		return modelLogin;

	}

	public ModelLogin consultaUsuarioID(String id, Long usuarioLogado) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login where id = ? and useradmin is false and usuario_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		statement.setLong(2, usuarioLogado);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) /* se tiver resultado */ {

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("cep"));
			modelLogin.setNumero(resultado.getString("numero"));

		}

		return modelLogin;

	}
	public ModelLogin consultaUsuarioID(Long id) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login where id = ? and useradmin is false";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, id);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) /* se tiver resultado */ {

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("cep"));
			modelLogin.setNumero(resultado.getString("numero"));

		}

		return modelLogin;

	}

	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws Exception {

		List<ModelLogin> retorno = new ArrayList<>();

		String sql = "SELECT * FROM model_login WHERE useradmin is false and usuario_id = " + userLogado + "limit 5";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {/* percorrer as linhas de resultado de sql */
			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			// modelLogin.setSenha(resultado.getString("senha"));

			retorno.add(modelLogin);

		}

		return retorno;

	}
	
	public List<ModelLogin> consultaUsuarioListPaginada(Long userLogado, Integer offSet) throws Exception {

				List<ModelLogin> retorno = new ArrayList<>();
		
				String sql = "SELECT * FROM model_login WHERE useradmin is false and usuario_id= " + userLogado + "order by nome offset "+ offSet +" limit 5";
		
				PreparedStatement statement = connection.prepareStatement(sql);
		
				ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {/* percorrer as linhas de resultado de sql */
			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			// modelLogin.setSenha(resultado.getString("senha"));

			retorno.add(modelLogin);

		}

		return retorno;

	}
	
	public int totalPagina(Long userLogado) throws Exception {
		String sql = "select count(1) as total from model_login where usuario_id = " + userLogado;

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();
		
		resultado.next();

		Double cadastros = resultado.getDouble("total");
		Double porPagina = 5.0;
		Double pagina = cadastros / porPagina;
		Double resto = pagina % 2;

		if (resto > 0) {
			pagina++;
		}
		return pagina.intValue();

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
