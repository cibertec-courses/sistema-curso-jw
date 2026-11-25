package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
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
		String sql = "INSERT INTO TB_Docente (nombreCompleto, correo, especialidad, horasSemanales, tarifaHora, fechaIngreso) "
				+ "VALUES (?,?,?,?,?,?) ";

		try (Connection cn = MySQLConnection.getConnection();
				PreparedStatement psm = cn.prepareStatement(sql);) {

			psm.setString(1, docente.getNombreCompleto());
			psm.setString(2, docente.getCorreo());
			psm.setString(3, docente.getEspecialidad());
			psm.setInt(4, docente.getHorasSemanales());
			psm.setDouble(5, docente.getTarifaHora());
			psm.setDate(6, Date.valueOf(docente.getFechaIngreso()));
			value = psm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}


		return value;
	}

	@Override
	public List<Docente> listarDocente() {
		// TODO Auto-generated method stub
		List<Docente> lista = new ArrayList<>();
		String sql = "SELECT * FROM TB_Docente";
		try (Connection cn = MySQLConnection.getConnection();
				PreparedStatement psm = cn.prepareStatement(sql);
				ResultSet rs = psm.executeQuery();	) {

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
		// TODO Auto-generated method stub
		Docente  docente = null;
		String sql = "SELECT * FROM TB_Docente WHERE idDocente = ?";
		try (Connection cn = MySQLConnection.getConnection();
				PreparedStatement psm = cn.prepareStatement(sql);) {
			psm.setInt(1, idDocente);
			try (ResultSet rs = psm.executeQuery();){
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
		// TODO Auto-generated method stub
		int value = 0;
		String sql = "UPDATE TB_Docente SET nombreCompleto = ?, correo = ?, especialidad = ?, "
				+ "horasSemanales = ?, tarifaHora = ?, fechaIngreso = ? "
				+ "WHERE idDocente = ?";

		try (Connection cn = MySQLConnection.getConnection();
				PreparedStatement psm = cn.prepareStatement(sql);) {

			psm.setString(1, docente.getNombreCompleto());
			psm.setString(2, docente.getCorreo());
			psm.setString(3, docente.getEspecialidad());
			psm.setInt(4, docente.getHorasSemanales());
			psm.setDouble(5, docente.getTarifaHora());
			psm.setDate(6, Date.valueOf(docente.getFechaIngreso()));
			psm.setInt(7, docente.getIdDocente());
			value = psm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}


		return value;
	}

	@Override
	public int eliminarDocente(int idDocente) {
		// TODO Auto-generated method stub
		int value = 0;
		String sql = "DELETE FROM TB_Docente "
				+ "WHERE idDocente = ?";
		try (Connection cn = MySQLConnection.getConnection();
				PreparedStatement psm = cn.prepareStatement(sql);) {
			psm.setInt(1, idDocente);
			value = psm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}


		return value;
	}

	@Override
	public boolean existeCorreo(String correo, int idExcluir) {
		boolean existe = false;

		String sql="SELECT COUNT(idDocente) FROM TB_Docente WHERE correo = ? AND idDocente <> ?";
		try (Connection cn = MySQLConnection.getConnection();
				PreparedStatement psm = cn.prepareStatement(sql);) {

			psm.setString(1, correo);
			psm.setInt(2, idExcluir);
			try (ResultSet rs = psm.executeQuery()){
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

		Docente d = new Docente();
		d.setIdDocente(rs.getInt("idDocente"));
		d.setNombreCompleto(rs.getString("nombreCompleto"));
		d.setCorreo(rs.getString("correo"));
		d.setEspecialidad(rs.getString("especialidad"));
		d.setHorasSemanales(rs.getInt("horasSemanales"));
		d.setTarifaHora(rs.getDouble("tarifaHora"));
		d.setFechaIngreso(rs.getDate("fechaIngreso").toLocalDate());
		return d;
	}

	@Override
	public List<Especialidad> listarEspecialidades() {
		// TODO Auto-generated method stub
		List<Especialidad> lista = new ArrayList<>();
		String sql = "SELECT * FROM TB_Especialidad";
		
		try(Connection cn = MySQLConnection.getConnection();
				PreparedStatement psm = cn.prepareStatement(sql);
				ResultSet rs = psm.executeQuery();){
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
