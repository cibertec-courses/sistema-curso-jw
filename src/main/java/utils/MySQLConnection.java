package utils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class MySQLConnection {

	private static final String URL = "jdbc:mysql://localhost:3306/BD_SistemaCurso"
			+ "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Lima";
	
	private static final String USR = "root";
	private static final String PSW = "mysql@2025";
	
	
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, USR, PSW);
		} catch(ClassNotFoundException e) {
			System.out.println("Error >> Driver no instalado: " + e.getMessage());
		}
		catch (Exception e) {
			System.out.println("Error >> de conexion con la BD; " + e.getMessage());
		}
		return con;
	}
}
