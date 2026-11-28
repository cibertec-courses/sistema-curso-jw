package dao;

import interfaces.CursoInterface;
import interfaces.DocenteInterface;
import interfaces.UsuarioInterface;
import modelo.CursoModel;
import modelo.DocenteModel;
import modelo.UsuarioModel;

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

	@Override
	public UsuarioInterface getUsuario() {
		// TODO Auto-generated method stub
		return new UsuarioModel();
	}

}
