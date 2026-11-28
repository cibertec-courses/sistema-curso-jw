package dao;

import interfaces.CursoInterface;
import interfaces.DocenteInterface;

public abstract class DAOFactory {
	/// Constantes que definen las fuentes datos 
	public static final int MYSQL= 1;
	public static final int SQLSERVER = 2;
	public static final int ORACLE = 3;
	public static final int POSTGRESSQL = 4;
	
	// Metodos de acceso a la implementacion
	
	public abstract CursoInterface getCurso();
	public abstract DocenteInterface getDocente();
	
	public static DAOFactory getDaoFactory (int tipo) {
		// TODO Auto-generated constructor stub
		switch (tipo) {
		case MYSQL:
			return new MysqlDAOFactory();
		case SQLSERVER:
			return new SQLServerDAOFactory();
			
		case ORACLE:
			return null;
			
		case POSTGRESSQL:
			return null;
		
		default:
			return null;
		}
	}
}