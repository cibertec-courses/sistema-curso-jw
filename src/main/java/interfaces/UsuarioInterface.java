package interfaces;

import entidades.Usuario;

public interface UsuarioInterface {

	Usuario login (String usuario, String clave);
	
	int registrarAcceso(int idUsuario);
	
	Usuario obtenerUsuario (int idUsuario);
	
	int actualizarDatos(Usuario usuario);
	
	
}
