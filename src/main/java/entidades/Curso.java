package entidades;

public class Curso {
	
	public int getidCurso() {
		return idCurso;
	}
	public void setidCurso(int curso) {
		idCurso = curso;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public String getProfesor() {
		return profesor;
	}
	public void setProfesor(String profesor) {
		this.profesor = profesor;
	}
	private int idCurso;
	private String codigo;
	private String nombre;
	private String nivel;
	private String profesor;
	
}
