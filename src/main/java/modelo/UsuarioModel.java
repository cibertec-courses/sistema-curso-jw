package modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import entidades.Docente;
import entidades.Rol;
import entidades.Usuario;
import interfaces.UsuarioInterface;
import utils.MySQLConnection;

public class UsuarioModel implements UsuarioInterface {

	@Override
	public Usuario login(String usuario, String clave) {
		Usuario u = null;

		try (Connection cn = MySQLConnection.getConnection();
				CallableStatement cs = cn.prepareCall("{call sp_login (?,?)}");
				){
			cs.setString(1, usuario);
			cs.setString(2, clave);

			try(ResultSet rs = cs.executeQuery()){
				if(rs.next()) {
					u = mapear(rs);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}


		return u;
	}

	@Override
	public int registrarAcceso(int idUsuario) {
		int value =0;
		try (Connection cn = MySQLConnection.getConnection();
				CallableStatement cs = cn.prepareCall("{call sp_registrar_acceso(?)}")) {
			cs.setInt(1, idUsuario);
			value = cs.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	@Override
	public Usuario obtenerUsuario(int idUsuario) {
		Usuario u = null;

		try (Connection cn = MySQLConnection.getConnection();
				CallableStatement cs = cn.prepareCall("{call sp_obtener_usuario (?)}");
				){
			cs.setInt(1, idUsuario);
			
			try(ResultSet rs = cs.executeQuery()){
				if(rs.next()) {
					u = mapear(rs);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}

	@Override
	public int actualizarDatos(Usuario usuario) {
		int value = 0;
		try (Connection cn = MySQLConnection.getConnection();
				CallableStatement cs = cn.prepareCall("{call sp_actualizar_datos_usuario(?,?,?,?)}")) {
			cs.setInt(1, usuario.getIdUsuario());
			cs.setString(2,usuario.getNombres());
			cs.setString(3,usuario.getApellidos());
			cs.setString(4, usuario.getCorreo());
			value = cs.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	private Usuario mapear(ResultSet rs) throws SQLException{
		Rol rol = new Rol();
		rol.setIdRol(rs.getInt("idRol"));
		rol.setNombre(rs.getString("rol"));

		Usuario  u = new Usuario();
		u.setIdUsuario(rs.getInt("idUsuario"));
		u.setUsuario(rs.getString("usuario"));
		u.setNombres(rs.getString("nombres"));
		u.setApellidos(rs.getString("apellidos"));
		u.setCorreo(rs.getString("correo"));
		u.setRol(rol);

		int idDocente = rs.getInt("idDocente");

		if(!rs.wasNull()) {
			Docente docente = new Docente();
			docente.setIdDocente(idDocente);
			u.setDocente(docente);
		}

		Timestamp acceso= rs.getTimestamp("ultimoAcceso");
		if(acceso !=null ) {
			u.setUltimoAcceso(acceso.toLocalDateTime());
		}
		return u;
	}


}
