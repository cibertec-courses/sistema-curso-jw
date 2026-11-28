package modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidades.Docente;
import entidades.Especialidad;
import interfaces.DocenteInterface;
import utils.MySQLConnection;

public class DocenteModel implements DocenteInterface {

	@Override
	public int registrarDocente(Docente docente) {
		int value = 0;
		String sql = "{call sp_registrar_docente(?,?,?,?,?,?)}";

		try (Connection cn = MySQLConnection.getConnection();
				CallableStatement cs = cn.prepareCall(sql);) {

			cs.setString(1, docente.getNombreCompleto());
			cs.setString(2, docente.getCorreo());
			cs.setInt(3, docente.getEspecialidad().getIdEspecialidad());
			cs.setInt(4, docente.getHorasSemanales());
			cs.setDouble(5, docente.getTarifaHora());
			cs.setDate(6, Date.valueOf(docente.getFechaIngreso()));
			value = cs.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}


		return value;
	}

	@Override
	public List<Docente> listarDocente() {
		List<Docente> lista = new ArrayList<>();
		String sql = "{call sp_listar_docentes()}";
		try (Connection cn = MySQLConnection.getConnection();
				CallableStatement cs = cn.prepareCall(sql);
				ResultSet rs = cs.executeQuery();	) {

			while(rs.next()) {
				lista.add(mapear(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public Docente obtenerDocente(int idDocente) {
		Docente  docente = null;
		String sql = "{call sp_obtener_docente(?)}";
		try (Connection cn = MySQLConnection.getConnection();
				CallableStatement cs = cn.prepareCall(sql);) {
			cs.setInt(1, idDocente);
			try (ResultSet rs = cs.executeQuery();){
				if (rs.next()) {
					docente = mapear(rs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return docente;
	}

	@Override
	public int actualizarDocente(Docente docente) {
		int value = 0;
		String sql = "{call sp_actualizar_docente(?,?,?,?,?,?,?)}";

		try (Connection cn = MySQLConnection.getConnection();
				CallableStatement cs = cn.prepareCall(sql);) {
			cs.setInt(1, docente.getIdDocente());
			cs.setString(2, docente.getNombreCompleto());
			cs.setString(3, docente.getCorreo());
			cs.setInt(4, docente.getEspecialidad().getIdEspecialidad());
			cs.setInt(5, docente.getHorasSemanales());
			cs.setDouble(6, docente.getTarifaHora());
			cs.setDate(7, Date.valueOf(docente.getFechaIngreso()));
			
			value = cs.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}


		return value;
	}

	@Override
	public int eliminarDocente(int idDocente) {
		int value = 0;
		String sql = "{call sp_eliminar_docente(?)}";
		try (Connection cn = MySQLConnection.getConnection();
				CallableStatement cs = cn.prepareCall(sql);) {
			cs.setInt(1, idDocente);
			value = cs.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}


		return value;
	}

	@Override
	public boolean existeCorreo(String correo, int idExcluir) {
		boolean existe = false;

		String sql="{call sp_existe_correo_docente(?,?)}";
		try (Connection cn = MySQLConnection.getConnection();
				CallableStatement cs = cn.prepareCall(sql);) {

			cs.setString(1, correo);
			cs.setInt(2, idExcluir);
			try (ResultSet rs = cs.executeQuery()){
				if (rs.next()) {
					existe = rs.getInt(1) > 0;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return existe;
	}

	private Docente mapear(ResultSet rs ) throws SQLException {
		Especialidad especialidad = new Especialidad();
		especialidad.setIdEspecialidad(rs.getInt("idEspecialidad"));
		especialidad.setNombre(rs.getString("especialidad"));
		
		
		Docente d = new Docente();
		d.setIdDocente(rs.getInt("idDocente"));
		d.setNombreCompleto(rs.getString("nombreCompleto"));
		d.setCorreo(rs.getString("correo"));
		d.setEspecialidad(especialidad);
		d.setHorasSemanales(rs.getInt("horasSemanales"));
		d.setTarifaHora(rs.getDouble("tarifaHora"));
		d.setFechaIngreso(rs.getDate("fechaIngreso").toLocalDate());
		return d;
	}

	@Override
	public List<Especialidad> listarEspecialidades() {
		List<Especialidad> lista = new ArrayList<>();
		String sql = "{call sp_listar_especialidades()}";

		try(Connection cn = MySQLConnection.getConnection();
				CallableStatement cs = cn.prepareCall(sql);
				ResultSet rs = cs.executeQuery();){
			while (rs.next()) {
				Especialidad especialidad = new Especialidad();
				especialidad.setIdEspecialidad(rs.getInt("idEspecialidad"));
				especialidad.setNombre(rs.getString("nombre"));
				lista.add(especialidad);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return lista;
	}





}
