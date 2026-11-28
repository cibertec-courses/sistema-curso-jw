package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLServerConnection {
	
	private static final String URL = "jdbc:sqlserver://100.65.41.105:1433;database=BD_SistemaCurso;"
			+ "encrypt=true;trustServerCertificate=true";
	
	private static final String USR = "sa";
	private static final String PSW = "sql@2025";
	
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(URL,USR,PSW);
		} catch(ClassNotFoundException e) {
			System.out.println("Error >> Driver no instalado: " + e.getMessage());
		}
		catch (Exception e) {
			System.out.println("Error >> de conexion con la BD; " + e.getMessage());
		}
		
		return con;
	}
	

}
