package utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import entidades.Usuario;

public class SesionProject {
	
	private static final DateTimeFormatter FORMATO= DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	
	private SesionProject() {
		// TODO Auto-generated constructor stub
	}
	
	public static void iniciar(HttpServletRequest request, Usuario usuario) {
		HttpSession anterior = request.getSession(false);
		if(anterior  != null ) {
			anterior.invalidate();
		}
		
		HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(Constantes.TIEMPO_INACTIVIDAD);
		session.setAttribute(Constantes.SESION_USUARIO_STRING, usuario);
		
		mostrarInfo(session);
		
	}
	
	
	public static Usuario getUsuario(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null) {
			return null;
		}
		return (Usuario) session.getAttribute(Constantes.SESION_USUARIO_STRING);
	}
	
	public static void actualziarUsuario( HttpServletRequest request, Usuario usuario) {
		HttpSession session = request.getSession(false);
		
		if(session !=null) {
			session.setAttribute(Constantes.SESION_USUARIO_STRING, usuario);
		}
		
	}
	
	public static void cerrar(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null ) {
			System.out.println("Sesion cerrada: " + session.getId());
			session.invalidate();
		}
	}
	
	
	
	public static void mostrarInfo(HttpSession session) {
		System.out.println("========== SESION ==========");
		System.out.println("ID                 : " + session.getId());
		System.out.println("Creacion           : " + formatear(session.getCreationTime()));
		System.out.println("Ultimo acceso      : " + formatear(session.getLastAccessedTime()));
		System.out.println("Max. inactividad   : " + session.getMaxInactiveInterval() + " segundos");
		System.out.println("Es nueva           : " + session.isNew());
		System.out.println("============================");
	}
	
	
	private static String formatear(long milisegundos) {

		LocalDateTime fecha= LocalDateTime.ofInstant(Instant.ofEpochMilli(milisegundos), ZoneId.systemDefault());
		return fecha.format(FORMATO);	
	}
	


}
