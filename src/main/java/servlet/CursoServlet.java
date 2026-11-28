package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import entidades.Curso;
import entidades.Docente;
import interfaces.CursoInterface;
import interfaces.DocenteInterface;

@WebServlet("/CursoServlet")
public class CursoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DAOFactory fabrica = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
	private CursoInterface modelo = fabrica.getCurso();
	private DocenteInterface docenteModelo = fabrica.getDocente();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String type = request.getParameter("type");
		if (type == null) {
			type = "list";
		}

		switch (type) {
		case "list":
			listarCursos(request, response);
			break;
		case "register":
			registrarCurso(request, response);
			break;
		case "get":
			obtenerCurso(request, response);
			break;
		case "update":
			actualizarCurso(request, response);
			break;
		case "delete":
			eliminarCurso(request, response);
			break;
		default:
			request.setAttribute("mensaje", "Opcion no valida: " + type);
			listarCursos(request, response);
		}
	}

	private void listarCursos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("data", modelo.listarCursos());
		if (docenteModelo != null) {
			request.setAttribute("docentes", docenteModelo.listarDocente());
		}
		request.getRequestDispatcher("gestion-cursos.jsp").forward(request, response);
	}

	private void registrarCurso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Curso curso = leerFormularioCurso(request);

		if (modelo.registarCurso(curso) == 1) {
			request.setAttribute("exito", "Curso " + curso.getCodigo() + " registrado correctamente.");
		} else {
			request.setAttribute("mensaje", "Ocurrio un problema: no se pudo registrar el curso (codigo repetido o docente no valido).");
			request.setAttribute("curso", curso);
		}
		listarCursos(request, response);
	}

	private void obtenerCurso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Curso curso = modelo.obtenerCurso(id);

		if (curso == null) {
			request.setAttribute("mensaje", "No existe curso con el ID " + id);
		} else {
			request.setAttribute("curso", curso);
		}
		listarCursos(request, response);
	}

	private void actualizarCurso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Curso curso = leerFormularioCurso(request);
		curso.setIdCurso(Integer.parseInt(request.getParameter("txtId")));

		if (modelo.actualizarCurso(curso) == 1) {
			request.setAttribute("exito", "Curso " + curso.getCodigo() + " actualizado correctamente.");
		} else {
			request.setAttribute("mensaje", "Ocurrio un problema: no se pudo actualizar el curso (codigo repetido o docente no valido).");
			request.setAttribute("curso", curso);
		}
		listarCursos(request, response);
	}

	private void eliminarCurso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		if (modelo.eliminarCurso(id) == 1) {
			request.setAttribute("exito", "Curso eliminado correctamente.");
		} else {
			request.setAttribute("mensaje", "No se pudo eliminar el curso.");
		}
		listarCursos(request, response);
	}

	private Curso leerFormularioCurso(HttpServletRequest request) {
		Docente docente = new Docente();
		docente.setIdDocente(Integer.parseInt(request.getParameter("txtDocente")));

		Curso curso = new Curso();
		curso.setCodigo(request.getParameter("txtCodigo"));
		curso.setNombre(request.getParameter("txtNombre"));
		curso.setNivel(request.getParameter("txtNivel"));
		curso.setDocente(docente);
		return curso;
	}

}
