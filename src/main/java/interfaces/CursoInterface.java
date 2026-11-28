package interfaces;


import java.util.List;

import entidades.Curso;

public interface CursoInterface {
	int registarCurso(Curso curso);
	
	List<Curso> listarCursos();
	
	Curso obtenerCurso(int idCurso);
	
	int actualizarCurso(Curso curso);
	
	int eliminarCurso(int idCurso);
	
	
}
