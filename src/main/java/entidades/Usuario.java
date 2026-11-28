package entidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Usuario {
	
	private int idUsuario;
	private String usuario;
	private String clave;
	private String nombres;
	private String apellidos;
	private String correo;
	private Rol rol;
	private Docente docente;
	private LocalDateTime ultimoAcceso;
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	public Docente getDocente() {
		return docente;
	}
	public void setDocente(Docente docente) {
		this.docente = docente;
	}
	public LocalDateTime getUltimoAcceso() {
		return ultimoAcceso;
	}
	public void setUltimoAcceso(LocalDateTime ultimoAcceso) {
		this.ultimoAcceso = ultimoAcceso;
	}

	public String getNombreCompleto() {
		return nombres + " " + apellidos;
	}

	public String getUltimoAccesoTexto() {
		if (ultimoAcceso == null) {
			return "Primer acceso";
		}
		return ultimoAcceso.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
	}

}
