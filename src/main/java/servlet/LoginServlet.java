package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import entidades.Usuario;
import interfaces.UsuarioInterface;
import utils.SesionProject;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsuarioInterface modelo = DAOFactory.getDaoFactory(DAOFactory.MYSQL).getUsuario();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String type = request.getParameter("type");
		if (type == null) {
			type = "";
		}

		switch (type) {
		case "login":
			login(request, response);
			break;
		case "logout":
			logout(request, response);
			break;
		default:
			response.sendRedirect("login.jsp");
		}
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String usuario = request.getParameter("txtUsuario");
		String clave = request.getParameter("txtClave");

		if (usuario == null || usuario.isBlank() || clave == null || clave.isBlank()) {
			volverAlLogin(request, response, "Ingrese usuario y clave");
			return;
		}

		Usuario u = modelo.login(usuario.trim(), clave);
		if (u == null) {
			volverAlLogin(request, response, "Usuario o clave incorrectos, o usuario inactivo");
			return;
		}

		modelo.registrarAcceso(u.getIdUsuario());
		SesionProject.iniciar(request, u);
		response.sendRedirect("index.jsp");
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SesionProject.cerrar(request);
		response.sendRedirect("login.jsp?salir=1");
	}

	private void volverAlLogin(HttpServletRequest request, HttpServletResponse response, String mensaje) throws ServletException, IOException {
		request.setAttribute("mensaje", mensaje);
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

}
