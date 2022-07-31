package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOLoginRepository {
	
	private Connection connection;
	
	public DAOLoginRepository() {
		
		connection = SingleConnectionBanco.getConnection();
		
	}
	/*método para validar o login*/
	
	public boolean validarAutenticacao(ModelLogin modelLogin)  throws Exception {
		
		String sql = "SELECT * from model_login where upper (login) = upper(?) and upper(senha) = upper(?) ";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			return true; /*autenticado*/
			
			
		}
		
		return false;/*não autenticado*/
		
	}
	

}
