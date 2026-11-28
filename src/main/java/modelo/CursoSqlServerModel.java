package modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidades.Curso;
import entidades.Docente;
import interfaces.CursoInterface;
import utils.SQLServerConnection;

public class CursoSqlServerModel implements CursoInterface {

	@Override
	public int registarCurso(Curso curso) {
		int value = 0;
		String sql = "{call sp_registrar_curso(?,?,?,?)}";
		try (Connection cn = SQLServerConnection.getConnection();
				CallableStatement cs = cn.prepareCall(sql)) {
			cs.setString(1, curso.getCodigo());
			cs.setString(2, curso.getNombre());
			cs.setString(3, curso.getNivel());
			cs.setInt(4, curso.getDocente().getIdDocente());
			value = cs.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	@Override
	public List<Curso> listarCursos() {
		List<Curso> lista = new ArrayList<>();
		String sql = "{call sp_listar_cursos}";
		try (Connection cn = SQLServerConnection.getConnection();
				CallableStatement cs = cn.prepareCall(sql);
				ResultSet rs = cs.executeQuery()) {
			while (rs.next()) {
				lista.add(mapear(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public Curso obtenerCurso(int idCurso) {
		Curso curso = null;
		String sql = "{call sp_obtener_curso(?)}";
		try (Connection cn = SQLServerConnection.getConnection();
				CallableStatement cs = cn.prepareCall(sql)) {
			cs.setInt(1, idCurso);
			try (ResultSet rs = cs.executeQuery()) {
				if (rs.next()) {
					curso = mapear(rs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return curso;
	}

	@Override
	public int actualizarCurso(Curso curso) {
		int value = 0;
		String sql = "{call sp_actualizar_curso(?,?,?,?,?)}";
		try (Connection cn = SQLServerConnection.getConnection();
				CallableStatement cs = cn.prepareCall(sql)) {
			cs.setInt(1, curso.getIdCurso());
			cs.setString(2, curso.getCodigo());
			cs.setString(3, curso.getNombre());
			cs.setString(4, curso.getNivel());
			cs.setInt(5, curso.getDocente().getIdDocente());
			value = cs.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	@Override
	public int eliminarCurso(int idCurso) {
		int value = 0;
		String sql = "{call sp_eliminar_curso(?)}";
		try (Connection cn = SQLServerConnection.getConnection();
				CallableStatement cs = cn.prepareCall(sql)) {
			cs.setInt(1, idCurso);
			value = cs.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	private Curso mapear(ResultSet rs) throws SQLException {
		Docente docente = new Docente();
		docente.setIdDocente(rs.getInt("idDocente"));
		docente.setNombreCompleto(rs.getString("docente"));

		Curso curso = new Curso();
		curso.setIdCurso(rs.getInt("idCurso"));
		curso.setCodigo(rs.getString("codigo"));
		curso.setNombre(rs.getString("nombre"));
		curso.setNivel(rs.getString("nivel"));
		curso.setDocente(docente);
		return curso;
	}

}
