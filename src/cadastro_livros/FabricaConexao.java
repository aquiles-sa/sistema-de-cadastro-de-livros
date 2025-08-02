package cadastro_livros;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {
	public static Connection getConexao() {
		try {
		    final String URL = "jdbc:mysql://localhost:3306/sua_base";
		    final String USUARIO = "root";
		    final String SENHA = "sua_senha";
			
			return DriverManager.getConnection(URL, USUARIO, SENHA);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
