package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;
import model.ModelTelefone;

public class DAOUsuarioRepository {

	private Connection connection;

	public DAOUsuarioRepository() {

		connection = SingleConnectionBanco.getConnection();
	}

	public ModelLogin gravarUsuario(ModelLogin objeto, Long usuarioLogado) throws Exception {

		if (objeto.isNovo()) {/* grava um novo */
			String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero, datanascimento, rendamensal) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

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
			preparedSQL.setDate(14, objeto.getDataNascimento());
			preparedSQL.setDouble(15, objeto.getRendaMensal());


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
			String sql = "UPDATE public.model_login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro=?, localidade=?, uf=?, numero=?, datanascimento=?, rendamensal=? WHERE id = "	+ objeto.getId() + ";";

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
			prepareSQL.setDate(13, objeto.getDataNascimento());
			prepareSQL.setDouble(14, objeto.getRendaMensal());

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
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendamensal"));


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
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendamensal"));
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
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendamensal"));

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
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendamensal"));

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
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendamensal"));

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
	public List<ModelLogin> consultaUsuarioListRel(Long userLogado) throws Exception {

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
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			
			modelLogin.setTelefones(this.listFone(modelLogin.getId()));


			retorno.add(modelLogin);

		}

		return retorno;

	}
	public List<ModelLogin> consultaUsuarioListRel(Long userLogado, String dataInicial, String dataFinal) throws Exception {

		List<ModelLogin> retorno = new ArrayList<>();

		String sql = "SELECT * FROM model_login WHERE useradmin is false and usuario_id = " + userLogado + " and datanascimento >= ? and datanascimento <= ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setDate(1, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
		statement.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {/* percorrer as linhas de resultado de sql */
			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			
			modelLogin.setTelefones(this.listFone(modelLogin.getId()));


			retorno.add(modelLogin);

		}

		return retorno;

	}


	public List<ModelTelefone> listFone(Long idUserPai) throws Exception {

		List<ModelTelefone> retorno = new ArrayList<ModelTelefone>();

		String sql = "select * from telefone where usuario_pai_id = ?";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setLong(1, idUserPai);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {

			ModelTelefone modelTelefone = new ModelTelefone();
			modelTelefone.setId(resultSet.getLong("id"));
			modelTelefone.setNumero(resultSet.getString("numero"));
			modelTelefone.setUsuario_cad_id(this.consultaUsuarioID(resultSet.getLong("usuario_cad_id")));
			modelTelefone.setUsuario_pai_id(this.consultaUsuarioID(resultSet.getLong("usuario_pai_id")));
			
			retorno.add(modelTelefone);
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
