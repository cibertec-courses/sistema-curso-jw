<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, entidades.Curso" %>
<%
    // Recogemos lo que dejó el Servlet
    @SuppressWarnings("unchecked")
    List<Curso> data = (List<Curso>) request.getAttribute("data");
    String exito   = (String) request.getAttribute("exito");
    String mensaje = (String) request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Gestión de Cursos</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="container mt-4">

  <%@ include file="/WEB-INF/menu.jspf" %>

  <%-- Mensajes del Servlet --%>
  <% if (exito != null) { %>
    <div class="alert alert-success"><%= exito %></div>
  <% } %>
  <% if (mensaje != null) { %>
    <div class="alert alert-danger"><%= mensaje %></div>
  <% } %>

  <div class="row">

    <%-- IZQUIERDA: formulario --%>
    <div class="col-lg-4">
      <h3>Registrar Cursos</h3>
      <form action="CursoServlet" method="post">
        <%-- Campo oculto: le dice al Servlet QUÉ hacer --%>
        <input type="hidden" name="type" value="register">

        <div class="mb-3">
          <label class="form-label">Código</label>
          <input class="form-control" type="text" name="txtCodigo" maxlength="10" required>
        </div>
        <div class="mb-3">
          <label class="form-label">Nombre</label>
          <input class="form-control" type="text" name="txtNombre" maxlength="100" required>
        </div>
        <div class="mb-3">
          <label class="form-label">Nivel</label>
          <select class="form-select" name="txtNivel" required>
            <option value="basico">Básico</option>
            <option value="intermedio">Intermedio</option>
            <option value="avanzado">Avanzado</option>
          </select>
        </div>
        <div class="mb-3">
          <label class="form-label">Profesor</label>
          <input class="form-control" type="text" name="txtProfesor" maxlength="100" required>
        </div>

        <button type="submit" class="btn btn-primary">Registrar</button>
        <a href="CursoServlet?type=list" class="btn btn-warning">Listar</a>
      </form>
    </div>

    <%-- DERECHA: tabla --%>
    <div class="col-lg-8">
      <h3>Cursos Disponibles</h3>
      <table class="table table-hover align-middle">
        <thead>
          <tr>
            <th>Id</th><th>Código</th><th>Nombre</th><th>Nivel</th><th>Profesor</th><th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          <% if (data == null) { %>
            <tr>
              <td colspan="6" class="text-danger">
                Abre esta página desde el Servlet: <a href="CursoServlet?type=list">CursoServlet?type=list</a>
              </td>
            </tr>
          <% } else { for (Curso item : data) { %>
            <tr>
              <td><%= item.getidCurso() %></td>
              <td><%= item.getCodigo() %></td>
              <td><%= item.getNombre() %></td>
              <td><%= item.getNivel() %></td>
              <td><%= item.getProfesor() %></td>
              <td>
                <%-- Se activan en el Tema 4 --%>
                <i class="bi bi-info-circle" title="Información"></i>
                <i class="bi bi-trash" title="Eliminar"></i>
              </td>
            </tr>
          <% } } %>
        </tbody>
      </table>
    </div>

  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>