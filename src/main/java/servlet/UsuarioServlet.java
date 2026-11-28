package servlet;

import java.io.IOException;

import javax.print.event.PrintJobAttributeEvent;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dao.DAOFactory;
import entidades.Usuario;
import interfaces.UsuarioInterface;
import utils.SesionProject;

/**
 * Servlet implementation class UsuarioServlet
 */
@WebServlet("/UsuarioServlet")
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	private UsuarioInterface modelo= DAOFactory.getDaoFactory(DAOFactory.MYSQL).getUsuario();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsuarioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    
    

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, resp);
		req.setCharacterEncoding("UTF-8");
		
		Usuario logueado = SesionProject.getUsuario(req);
		
		if (logueado == null) {
			resp.sendRedirect("login.jsp");
			return;
		}
		
		String type = req.getParameter("type");
		if(type == null) {
			type = "misdatos";
		}
		
		switch (type) {
		case "actualizar":
			actualizarDatos(req, resp, logueado);
			break;
		default:
			req.getRequestDispatcher("misDatos.jsp").forward(req, resp);
		}
		
		
	}

	private void actualizarDatos(HttpServletRequest request, HttpServletResponse response, Usuario logueado) throws ServletException, IOException {
		String nombres = limpiar(request.getParameter("txtNombres"));
		String apellidos = limpiar(request.getParameter("txtApellidos"));
		String correo = limpiar(request.getParameter("txtCorreo")).toLowerCase();
		
		String error =  null;
		
		if(nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty()) {
			error = "Todo los campos son obligatorios";
		}else if( !nombres.matches("^[A-Za-zÁÉÍÓÚÜáéíóúüÑñ ]{2,50}$") || !apellidos.matches("^[A-Za-zÁÉÍÓÚÜáéíóúüÑñ ]{2,50}$") ) {
			error = "Nombres y apellidos solo letras y espacios (2 a 50 caracteres).";
		}else if(!correo.matches("^[a-zA-Z0-9]+([._-][a-zA-Z0-9]+)*@cibertec\\.edu\\.pe$")) {
			error="El Correo debe terminar en @cibertec.edu.pe";
		}
		
		if(error != null ) {
			request.setAttribute("mensaje", error);
			request.getRequestDispatcher("misDatos.jsp").forward(request, response);
			return;
		}
		
		Usuario cambios = new Usuario();
		
		cambios.setIdUsuario(logueado.getIdUsuario());
		cambios.setNombres(nombres);
		cambios.setApellidos(apellidos);
		cambios.setCorreo(correo);
		
		if(modelo.actualizarDatos(cambios)==1) {
			logueado.setNombres(nombres);
			logueado.setApellidos(apellidos);
			logueado.setCorreo(correo);
			
			SesionProject.actualziarUsuario(request, logueado);
			
			request.setAttribute("exito", "Datos actualizados correctamente.");
			
		}else {
			request.setAttribute("mensaje","No se pudo actualizar. Verifique que el correo no este en uso");
		}
		request.getRequestDispatcher("misDatos.jsp").forward(request, response);	
	}
	
	private String limpiar(String valor) {
		return valor== null ? "" : valor.trim();
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
