package servlet;

import java.io.IOException;
import java.net.ResponseCache;
import java.net.Authenticator.RequestorType;

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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UsuarioInterface modelo = DAOFactory.getDaoFactory(DAOFactory.MYSQL).getUsuario();
	
    
	
    @Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, resp);
		req.setCharacterEncoding("UTF-8");
		
		String type = req.getParameter("type");
		if(type == null ) {
			type="";
		}
		
		switch (type) {
		case "login":
			login(req, resp);
			break;
			
		case "logout":
			logout(req, resp);
			break;


		default:
			resp.sendRedirect("login.jsp");
		}
		
	}

    
    private void login(HttpServletRequest request, HttpServletResponse responses) throws ServletException, IOException {
    	String usuario = request.getParameter("txtUsuario");
    	String clave = request.getParameter("txtClave");
    	
    	if(usuario == null || usuario.isBlank() || clave == null || clave.isBlank()) {
    		volverAlLogin(request, responses, "Ingrese usuario y clave");
    		return;
    	}
    	Usuario u = modelo.login(usuario.trim(), clave);
    	
    	if(u== null) {
    		volverAlLogin(request, responses, "Usuario o clave incorrectos, o usuario inactivo");
    		return;
    	}
    	modelo.registrarAcceso(u.getIdUsuario());
    	SesionProject.iniciar(request, u);
    	responses.sendRedirect("index.jsp");
    	
    }
    
    private void logout(HttpServletRequest request, HttpServletResponse responses) throws ServletException, IOException {
    	SesionProject.cerrar(request);
    	responses.sendRedirect("login.jsp?salir=1");
    }
    	  
    
    
    private void volverAlLogin(HttpServletRequest request, HttpServletResponse responses, String mensaje) throws ServletException, IOException {
    	request.setAttribute("mensaje", mensaje);
    	request.getRequestDispatcher("login.jsp").forward(request, responses);
    }
    
    
	/**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
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
