package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {

	private Connection connection;

	public DAOUsuarioRepository() {

		connection = SingleConnectionBanco.getConnection();
	}

	public void gravarUsuario(ModelLogin objeto) throws Exception {

		

			String sql = "INSERT INTO model_login(login, senha, nome, email) VALUES (?, ?, ?, ?);";

			PreparedStatement preparedSQL = connection.prepareStatement(sql);
			preparedSQL.setString(1, objeto.getLogin());
			preparedSQL.setString(2, objeto.getSenha());
			preparedSQL.setString(3, objeto.getNome());
			preparedSQL.setString(4, objeto.getEmail());
			
			preparedSQL.execute();
			connection.commit();

	}

}
