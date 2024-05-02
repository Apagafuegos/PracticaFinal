package equiposService;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dao {
	
	protected Connection getNewConnection() {
		String url = "jdbc:mysql://localhost:3306/practica_final";
		String driver = "com.mysql.cj.jdbc.Driver";
		String user = "user";
		String password = "user1234";

		try {
			Class.forName(driver);
			return DriverManager.getConnection(url, user, password);

		} catch (Exception e) {
			System.err.println("No se ha podido conectar a la BBDD" + e.getMessage());
			e.getStackTrace();
			throw new RuntimeException("No se pudo conectar a la BBDD");
		}

	}

	public void testConexion() {
		Connection conn = getNewConnection();
		if (conn == null) System.err.println("No hay conexion");
        else {
			System.out.println("¡Hay conexión!");
		}
	}
	
}
