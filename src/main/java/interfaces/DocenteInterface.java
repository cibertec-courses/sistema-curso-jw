package interfaces;

import java.util.List;

import entidades.Docente;
import entidades.Especialidad;

public interface DocenteInterface {
	
	int registrarDocente(Docente docente);
	
	List<Docente> listarDocente();
	
	Docente obtenerDocente (int idDocente);
	
	int actualizarDocente (Docente docente);
	
	int eliminarDocente(int idDocente);
	
	boolean existeCorreo(String correo, int idExcluir);
	
	List<Especialidad> listarEspecialidades();
	

}
