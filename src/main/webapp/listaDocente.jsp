<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@ page
	import="java.util.List, java.time.format.DateTimeFormatter, entidades.Docente"%>
<%
//Datos que envia el ServleDocente
@SuppressWarnings("unchecked")
List<Docente> data = (List<Docente>) request.getAttribute("data");
String exito = (String) request.getAttribute("exito");
String mensaje = (String) request.getAttribute("mensaje");

/// Formato de fecha

DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Lista de Docentes</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css">
</head>
<body class="container mt-4">
	<%@ include file="/WEB-INF/menu.jspf"%>

	<%
	if (exito != null) {
	%>
	<div class="alert alert-success">
		<%=exito%></div>
	<%
	}
	%>

	<%
	if (mensaje != null) {
	%>
	<div class="alert alert-danger">
		<%=mensaje%></div>
	<%
	}
	%>

	<div class="d-flex justify-content-between align-items-center mb-3">
		<h3>Lista Docentes</h3>
		<a href="DocenteServlet?type=new" class="btn btn-primary">Nuevo
			Docente</a>
	</div>

	<div class="table-responsive">
		<table class="table table-striped table-hover align-middle">
			<thead class="table-dark">
				<tr>
					<th>Id</th>
					<th>Nombre completo</th>
					<th>Correo</th>
					<th>Especialidad</th>
					<th class="text-end">Horas</th>
					<th class="text-end">Tarifa (S/)</th>
					<th>Fecha ingreso</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<% if (data == null ){ %>
				<tr>
					<td colspan="8" class="text-danger">
						Abra esta pagina desde el menu <a href="DocenteServlet?type=list">DocenteServlet?type=list</a>
					</td>
				</tr>
				<%} else if (data.isEmpty()){ %>
				<tr>
					<td colspan="8" class="text-muted">
						No hay docentes registrados
					</td>
				</tr>
				<%} else{%>
					<% for (Docente d : data) { %>
					<tr>
						<td><%= d.getIdDocente() %></td>
						<td><%= d.getNombreCompleto() %></td>
						<td><%= d.getCorreo() %></td>
						<td><%= d.getEspecialidad().getNombre() %></td>
						<td class="text-end"><%= d.getHorasSemanales() %></td>
						<td class="text-end"><%= String.format("%.2f", d.getTarifaHora()) %></td>
						<td><%= d.getFechaIngreso().format(formatoFecha) %></td>
						<td class="text-nowrap">
							<a href="DocenteServlet?type=edit&id=<%= d.getIdDocente() %>" class="btn btn-sm btn-warning">Editar</a>
							<a href="DocenteServlet?type=delete&id=<%= d.getIdDocente() %>" class="btn btn-sm btn-danger"
							   onclick="return confirm('¿Seguro que desea eliminar este docente?');">Eliminar</a>
						</td>
					</tr>
					<% } %>
				<%}%>
			</tbody>
		</table>
	</div>

	<script
			src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>