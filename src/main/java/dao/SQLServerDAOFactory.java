package dao;

import interfaces.CursoInterface;
import interfaces.DocenteInterface;
import interfaces.UsuarioInterface;
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

	@Override
	public UsuarioInterface getUsuario() {
		// TODO Auto-generated method stub
		return null;
	}

}
