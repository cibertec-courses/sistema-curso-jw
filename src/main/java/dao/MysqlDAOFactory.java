package dao;

import interfaces.CursoInterface;
import interfaces.DocenteInterface;
import modelo.CursoModel;
import modelo.DocenteModel;

public class MysqlDAOFactory extends DAOFactory{

	@Override
	public CursoInterface getCurso() {
		// TODO Auto-generated method stub
		return new CursoModel();
	}

	@Override
	public DocenteInterface getDocente() {
		// TODO Auto-generated method stub
		return new DocenteModel();
	}

}
