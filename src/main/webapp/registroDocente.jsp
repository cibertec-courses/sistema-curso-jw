<%@page import="entidades.Especialidad"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.List, java.util.Locale, java.time.LocalDate, entidades.Docente"%>
<%!private String valor(String escrito, String delDocente) {
		if (escrito != null)
			return escrito;
		if (delDocente != null)
			return delDocente;
		return "";
	}%>
<%
	Docente docente = (Docente) request.getAttribute("docente");
	@SuppressWarnings("unchecked")
	List<String> errores = (List<String>) request.getAttribute("errores");

	String id = valor(request.getParameter("txtId"), docente != null ? String.valueOf(docente.getIdDocente()) : null);
	boolean editando = !id.isEmpty() && !id.equals("0");

	String nombre       = valor(request.getParameter("txtNombre"),       docente != null ? docente.getNombreCompleto() : null);
	String correo       = valor(request.getParameter("txtCorreo"),       docente != null ? docente.getCorreo() : null);
	String especialidad = valor(request.getParameter("txtEspecialidad"), docente != null ? docente.getEspecialidad() : null);
	String horas        = valor(request.getParameter("txtHoras"),        docente != null ? String.valueOf(docente.getHorasSemanales()) : null);
	String tarifa       = valor(request.getParameter("txtTarifa"),       docente != null ? String.format(Locale.US, "%.2f", docente.getTarifaHora()) : null);
	String fecha        = valor(request.getParameter("txtFecha"),        docente != null ? docente.getFechaIngreso().toString() : null);

	String hoy = LocalDate.now().toString();
	///String[] especialidades = {"Programación", "Base de Datos", "Redes", "Diseño Web"};
	@SuppressWarnings("unchecked")
	List<Especialidad> especialidades = (List<Especialidad>) request.getAttribute("especialidades");
	
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><%= editando ? "Actualizar Docente" : "Registrar Docente" %></title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css">
</head>
<body class="container mt-4">

	<%@ include file="/WEB-INF/menu.jspf"%>

	<div class="row justify-content-center">
		<div class="col-lg-7">
			<div class="card shadow-sm">
				<div class="card-body p-4">

					<h3 class="mb-3"><%= editando ? "Actualizar Docente" : "Registrar Docente" %></h3>

					<% if (errores != null && !errores.isEmpty()) { %>
					<div class="alert alert-danger">
						<strong>Corrija los siguientes datos:</strong>
						<ul class="mb-0">
							<% for (String e : errores) { %>
							<li><%= e %></li>
							<% } %>
						</ul>
					</div>
					<% } %>

					<form action="ServletDocente" method="post" id="formDocente"
						novalidate>
						<input type="hidden" name="type"
							value="<%= editando ? "update" : "register" %>"> <input
							type="hidden" name="txtId" value="<%= editando ? id : "" %>">

						<div class="mb-3">
							<label for="txtNombre" class="form-label">Nombre completo</label>
							<input type="text" class="form-control" id="txtNombre"
								name="txtNombre" maxlength="60" value="<%= nombre %>">
						</div>

						<div class="mb-3">
							<label for="txtCorreo" class="form-label">Correo</label> <input
								type="text" class="form-control" id="txtCorreo" name="txtCorreo"
								maxlength="80" placeholder="usuario@cibertec.edu.pe"
								value="<%= correo %>">
						</div>

						<div class="mb-3">
							<label for="txtEspecialidad" class="form-label">Especialidad</label>
							<select class="form-select" id="txtEspecialidad"
								name="txtEspecialidad">
								<option value="">-- Seleccione --</option>
								<%  if (especialidades != null){
								for (Especialidad esp : especialidades) { %>
								<option value="<%= esp.getNombre() %>"
									<%= esp.getNombre().equals(especialidad) ? "selected" : "" %>><%= esp.getNombre() %></option>
								<% }
								}%>
							</select>
						</div>

						<div class="row">
							<div class="col-md-4 mb-3">
								<label for="txtHoras" class="form-label">Horas semanales</label>
								<input type="text" class="form-control" id="txtHoras"
									name="txtHoras" value="<%= horas %>">
							</div>
							<div class="col-md-4 mb-3">
								<label for="txtTarifa" class="form-label">Tarifa por
									hora (S/)</label> <input type="text" class="form-control"
									id="txtTarifa" name="txtTarifa" placeholder="45.50"
									value="<%= tarifa %>">
							</div>
							<div class="col-md-4 mb-3">
								<label for="txtFecha" class="form-label">Fecha de
									ingreso</label> <input type="date" class="form-control" id="txtFecha"
									name="txtFecha" max="<%= hoy %>" value="<%= fecha %>">
							</div>
						</div>

						<button type="submit" class="btn btn-primary"><%= editando ? "Actualizar" : "Registrar" %></button>
						<a href="ServletDocente?type=list" class="btn btn-secondary">Volver
							al listado</a>
					</form>

				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/jquery-validation@1.21.0/dist/jquery.validate.min.js"></script>

	<script type="text/javascript">
	$(function() {

		$.validator.setDefaults({
			errorElement: "div",
			errorPlacement: function(error, element) {
				error.addClass("invalid-feedback");
				error.insertAfter(element);
			},
			highlight: function(element) {
				$(element).addClass("is-invalid").removeClass("is-valid");
			},
			unhighlight: function(element) {
				$(element).removeClass("is-invalid").addClass("is-valid");
			}
		});

		$.validator.addMethod("soloLetras", function(value, element) {
			return this.optional(element) || /^[A-Za-zÁÉÍÓÚÜáéíóúüÑñ ]+$/.test(value);
		});
		$.validator.addMethod("correoCibertec", function(value, element) {
			return this.optional(element) || /^[a-zA-Z0-9]+([._-][a-zA-Z0-9]+)*@cibertec\.edu\.pe$/.test(value);
		});
		$.validator.addMethod("dosDecimales", function(value, element) {
			return this.optional(element) || /^\d{1,3}\.\d{2}$/.test(value);
		});
		$.validator.addMethod("noFutura", function(value, element) {
			return this.optional(element) || value <= "<%= hoy %>
		";
			});

			$("#formDocente")
					.validate(
							{
								rules : {
									txtNombre : {
										required : true,
										minlength : 5,
										maxlength : 60,
										soloLetras : true
									},
									txtCorreo : {
										required : true,
										correoCibertec : true
									},
									txtEspecialidad : {
										required : true
									},
									txtHoras : {
										required : true,
										digits : true,
										range : [ 4, 40 ]
									},
									txtTarifa : {
										required : true,
										dosDecimales : true,
										range : [ 25, 200 ]
									},
									txtFecha : {
										required : true,
										dateISO : true,
										noFutura : true
									}
								},
								messages : {
									txtNombre : {
										required : "El nombre completo es obligatorio",
										minlength : "Mínimo 5 caracteres",
										maxlength : "Máximo 60 caracteres",
										soloLetras : "Solo letras (con o sin tilde) y espacios"
									},
									txtCorreo : {
										required : "El correo es obligatorio",
										correoCibertec : "Formato válido y debe terminar en @cibertec.edu.pe"
									},
									txtEspecialidad : {
										required : "Seleccione una especialidad"
									},
									txtHoras : {
										required : "Las horas semanales son obligatorias",
										digits : "Solo números enteros",
										range : "Debe estar entre 4 y 40"
									},
									txtTarifa : {
										required : "La tarifa es obligatoria",
										dosDecimales : "Debe tener 2 decimales (ejemplo: 45.50)",
										range : "Debe estar entre 25.00 y 200.00"
									},
									txtFecha : {
										required : "La fecha de ingreso es obligatoria",
										dateISO : "Fecha no válida",
										noFutura : "No puede ser mayor a la fecha actual"
									}
								},
								submitHandler : function(form) {
									form.submit();
								}
							});
		});
	</script>
</body>
</html>