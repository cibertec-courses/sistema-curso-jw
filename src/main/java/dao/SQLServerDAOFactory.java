package dao;

import interfaces.CursoInterface;
import interfaces.DocenteInterface;
import modelo.CursoSqlServerModel;

public class SQLServerDAOFactory extends DAOFactory {

	@Override
	public CursoInterface getCurso() {
		return new CursoSqlServerModel();
	}

	@Override
	public DocenteInterface getDocente() {
		return null;
	}

}
