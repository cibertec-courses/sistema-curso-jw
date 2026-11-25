package interfaces;


import java.util.List;

import entidades.Curso;

public interface CursoInterface {
	int registarCurso(Curso curso);
	
	List<Curso> listarCursos();
}
