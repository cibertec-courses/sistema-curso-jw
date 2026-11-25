package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import entidades.Docente;
import entidades.Especialidad;
import interfaces.DocenteInterface;
import modelo.DocenteModel;

/**
 * Servlet implementation class ServletDocente
 */
@WebServlet("/DocenteServlet")
public class DocenteSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DocenteInterface model =DAOFactory.getDaoFactory(DAOFactory.MYSQL).getDocente();

	// VALORES TEMPORALES


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DocenteSevlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static final List<String> ESPECIALIDADES = 
			List.of("Programación", "Base de Datos", "Redes", "Diseño Web");


	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");

		String type = req.getParameter("type");

		if(type == null) {
			type = "list";
		}


		switch (type) {
		case "list":
			listar(req, resp);
			break;
		case "new":
			nuevo(req, resp);
			break;
		case "edit":
			editar(req, resp);
			break;
		case "register":
			registrar(req, resp);
			break;
		case "update":
			actualizar(req, resp);
			break;
		case "delete":
			eliminar(req, resp);
			break;
		default:
			req.setAttribute("mensaje", "Opcion no valida: "+ type);
			listar(req, resp);
		}


	}

	private void cargarEspecialidades(HttpServletRequest request) {
		request.setAttribute("especialidades", model.listarEspecialidades());
	}
	
	private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setAttribute("data", model.listarDocente());
		request.getRequestDispatcher("listaDocente.jsp").forward(request, response);
	}

	private void nuevo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		cargarEspecialidades(request);
		request.getRequestDispatcher("registroDocente.jsp").forward(request, response);
	}

	private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int id = Integer.parseInt(request.getParameter("id"));
		Docente d = model.obtenerDocente(id);

		if(d == null) {
			request.setAttribute("mensaje", "No existe el docente con ID "+id);
			listar(request, response);
			return;
		}

		request.setAttribute("docente", d);
		cargarEspecialidades(request);
		request.getRequestDispatcher("registroDocente.jsp").forward(request, response);

	}


	private void registrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<String> errores = new ArrayList<>();
		Docente d = leerYValidar(request, errores, 0);

		if(!errores.isEmpty()) {
			volverAlFormulario(request, response, errores);
			return;
		}
		if(model.registrarDocente(d)== 1) {
			request.setAttribute("exito", "Docente "+ d.getNombreCompleto() + " registrado correctamente.");
			listar(request, response);
		}else {
			errores.add("Ocurrio un problema al registrar en la BBDD");
			volverAlFormulario(request, response, errores);
		}

	}

	private void actualizar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int id = Integer.parseInt(request.getParameter("txtId"));
		List<String> errores = new ArrayList<>();
		Docente d = leerYValidar(request, errores, id);

		if(!errores.isEmpty()) {
			volverAlFormulario(request, response, errores);
			return;
		}

		d.setIdDocente(id);
		if(model.actualizarDocente(d) == 1) {
			request.setAttribute("exito", "Docente "+ d.getNombreCompleto() + " actualizado correctamente.");
			listar(request, response);
		}else {
			errores.add("Ocurrio un problema al actualizar en la BBDD");
			volverAlFormulario(request, response, errores);
		}
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int id = Integer.parseInt(request.getParameter("id"));

		if(model.eliminarDocente(id) == 1) {
			request.setAttribute("exito", "Docente eliminado correctamente.");
		}else {
			request.setAttribute("mensaje", "No se pudo eliminar el docente.");
		}
		listar(request, response);
	}

	private Docente leerYValidar(HttpServletRequest request, List<String> errores, int idActual) {
		Docente d = new Docente();

		// Validar Nombre Completo

		String nombre = limpiar(request.getParameter("txtNombre"));
		if (nombre.isEmpty()) {
			errores.add("El nombre completo es obligatorio.");
		}else if(!nombre.matches("^[A-Za-zÁÉÍÓÚÜáéíóúüÑñ ]{5,60}$")) {
			errores.add("El nombre debe tener de 5 a 60 caracteres y solo letras (con o sin tilde) y espacios");
		}

		d.setNombreCompleto(nombre);

		// Validar Correo 
		String correo = limpiar(request.getParameter("txtCorreo")).toLowerCase();
		if(correo.isEmpty()) {
			errores.add("El correo es obligatorio");
		}else if (!correo.matches("^[a-zA-Z0-9]+([._-][a-zA-Z0-9]+)*@cibertec\\.edu\\.pe$")) {
			errores.add("El correo debe tener un formato valido y terminar en @cibertec.edu.pe");
		}else if (model.existeCorreo(correo, idActual)) {
			errores.add("El correo " + correo + " ya esta registrado en otro docente.");
		}
		d.setCorreo(correo);

		// Validad Especialidad
		String especialidad = limpiar(request.getParameter("txtEspecialidad"));
		if(especialidad.isEmpty()) {
			errores.add("La especialidad es obligatoria");
		}else if (!esEspecialidadValida(especialidad)) {
			errores.add("Especialidad no permitida");
		}
		d.setEspecialidad(especialidad);

		// Validar Horas Semanales

		String horas = limpiar(request.getParameter("txtHoras"));
		if(horas.isEmpty()) {
			errores.add("Las horas semanales son obligatorias");
		}else if (!horas.matches("^\\d+$")) {
			errores.add("Las horas semanales deben ser un numero entero");
		}else {
			int h = Integer.parseInt(horas);
			if(h < 4 || h > 40) {
				errores.add("Las horas semanales deben estar entre 4 y 40.");
			}
			d.setHorasSemanales(h);
		}

		// Tarifa por hora 

		String tarifa = limpiar(request.getParameter("txtTarifa"));
		if(tarifa.isEmpty()) {
			errores.add("La tarifa es obligatoria.");
		}else if(!tarifa.matches("^\\d{1,3}\\.\\d{2}$")) {
			errores.add("La tarifa debe ser un numero con 2 decimales");
		}else {
			double t = Double.parseDouble(tarifa);
			if (t < 25.00 || t>200.00) {
				errores.add("La tarifa debe ser entre 25.00 y 200.00");
			}
			d.setTarifaHora(t);
		}

		// Fecha de ingreso 

		String fecha = limpiar(request.getParameter("txtFecha"));

		if (fecha.isEmpty()) {
			errores.add("La fecha es obligatoria");
		}else {
			try {
				LocalDate f = LocalDate.parse(fecha);
				if (f.isAfter(LocalDate.now())) {
					errores.add("La fecha de ingreso no puede ser mayor a la fecha actual");
				}
				d.setFechaIngreso(f);
			} catch (DateTimeParseException e) {
				errores.add("La fecha de ingreso no es valida");
			}
		}

		return d;

	}
	private boolean esEspecialidadValida(String nombre) {
		for (Especialidad e: model.listarEspecialidades()) {
			if(e.getNombre().equals(nombre)) {
				return true;
			}
		}
		return false;
	}

	private void volverAlFormulario(HttpServletRequest request, HttpServletResponse response, List<String> errores) throws ServletException, IOException{
		request.setAttribute("errores", errores);
		cargarEspecialidades(request);
		request.getRequestDispatcher("registroDocente.jsp").forward(request, response);
	}


	private String limpiar (String valor) {
		return valor == null ? "" : valor.trim(); 
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