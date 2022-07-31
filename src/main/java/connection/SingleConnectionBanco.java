package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {

	private static String banco = "jdbc:postgresql://localhost:5433/projeto.jsp?autoReconnect=true";
	private static String user = "postgres";
	private static String senha = "admin";
	private static Connection connection = null;
	
	
	public static Connection getConnection() {
		return connection;
	}

	static {
		Conectar();
	}

	public SingleConnectionBanco() {// quando titer uma instancia vai conectar

		Conectar();

	}

	private static void Conectar() {

		try {

			if (connection == null) {

				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(banco, user, senha);
				connection.setAutoCommit(false);
			}

		} catch (Exception e) {

			e.printStackTrace();/* mostrar qualquer erro */

		}

	}

}
