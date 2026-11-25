package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import entidades.Curso;
import interfaces.CursoInterface;
import modelo.CursoModel;

/**
 * Servlet implementation class CursoServlet
 */
@WebServlet("/CursoServlet")
public class CursoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CursoInterface modelo = DAOFactory.getDaoFactory(DAOFactory.MYSQL).getCurso();


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CursoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	protected void service ( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");

		String type = request.getParameter("type");
		if(type == null) {
			type = "list";
		}

		switch (type) {
		case "list": 
			listarCursos(request, response);
			break;
		case "register": 
			registarCursos(request, response);
			break;
		default:
			request.setAttribute("mensaje", "Opcion no valida: "+ type);
			listarCursos(request, response);
		}
	}


	private void listarCursos( HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{
		List<Curso>  data = modelo.listarCursos();
		request.setAttribute("data",data);
		request.getRequestDispatcher("gestion-cursos.jsp").forward(request, response);
	}


	private void registarCursos( HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{

		/// 1. Entradas del formulario (name de los input)
		String codigo = request.getParameter("txtCodigo");
		String nombre = request.getParameter("txtNombre");
		String nivel = request.getParameter("txtNivel");
		String profesor = request.getParameter("txtProfesor");

		// 2. Crear obj
		Curso curso =  new Curso();
		curso.setCodigo(codigo);
		curso.setNombre(nombre);
		curso.setNivel(nivel);
		curso.setProfesor(profesor);

		int value = modelo.registarCurso(curso);

		if(value == 1 ) {
			request.setAttribute("exito", "Curso "+ codigo + "  registrado correctamente ");

		}else {
			request.setAttribute("mensaje", "Ocurrio un problema: no se pudo registrar curso.");
		}
		listarCursos(request, response);
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
