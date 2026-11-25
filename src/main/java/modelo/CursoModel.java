package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entidades.Curso;
import interfaces.CursoInterface;
import utils.MySQLConnection;



public class CursoModel implements CursoInterface {

	@Override
	public int registarCurso(Curso curso) {
		// TODO Auto-generated method stub
		int value = 0;
		String sql = "INSERT INTO TB_Curso (codigo, nombre, nivel, profesor) values (?,?,?,?)";
		try (Connection cn = MySQLConnection.getConnection();
				PreparedStatement psm = cn.prepareStatement(sql)) {		
			psm.setString(1, curso.getCodigo());
			psm.setString(2, curso.getNombre());
			psm.setString(3, curso.getNivel());
			psm.setString(4, curso.getProfesor());
			
			value = psm.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
	}

	@Override
	public List<Curso> listarCursos() {
		// TODO Auto-generated method stub
		List<Curso>  lista = new ArrayList<>();
		String sql = "SELECT idCurso,codigo, nombre, nivel, profesor FROM TB_Curso Order by idcurso";
		try (Connection cn = MySQLConnection.getConnection();
				PreparedStatement psm = cn.prepareStatement(sql);
				ResultSet rs = psm.executeQuery()) {
			while (rs.next()) {
				Curso c = new Curso();
				c.setidCurso(rs.getInt("idCurso"));
				c.setCodigo(rs.getString("codigo"));
				c.setNombre(rs.getString("nombre"));
				c.setNivel(rs.getString("nivel"));
				c.setProfesor(rs.getString("profesor"));
				
				lista.add(c);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return lista;
	}

	
	

}
