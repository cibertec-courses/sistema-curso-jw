<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, entidades.Curso, entidades.Docente" %>
<%
    @SuppressWarnings("unchecked")
    List<Curso> data = (List<Curso>) request.getAttribute("data");
    @SuppressWarnings("unchecked")
    List<Docente> docentes = (List<Docente>) request.getAttribute("docentes");
    String exito   = (String) request.getAttribute("exito");
    String mensaje = (String) request.getAttribute("mensaje");

    Curso curso = (Curso) request.getAttribute("curso");
    boolean editando = curso != null && curso.getIdCurso() > 0;

    String codigo    = curso != null ? curso.getCodigo() : "";
    String nombre    = curso != null ? curso.getNombre() : "";
    String nivel     = curso != null ? curso.getNivel() : "";
    int idDocente    = curso != null ? curso.getDocente().getIdDocente() : 0;

    String[][] niveles = {{"basico", "Básico"}, {"intermedio", "Intermedio"}, {"avanzado", "Avanzado"}};
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

  <% if (exito != null) { %>
    <div class="alert alert-success"><%= exito %></div>
  <% } %>
  <% if (mensaje != null) { %>
    <div class="alert alert-danger"><%= mensaje %></div>
  <% } %>

  <div class="row">

    <div class="col-lg-4">
      <h3><%= editando ? "Actualizar Curso" : "Registrar Curso" %></h3>
      <form action="CursoServlet" method="post">
        <input type="hidden" name="type" value="<%= editando ? "update" : "register" %>">
        <input type="hidden" name="txtId" value="<%= editando ? curso.getIdCurso() : "" %>">

        <div class="mb-3">
          <label class="form-label">Código</label>
          <input class="form-control" type="text" name="txtCodigo" maxlength="10" value="<%= codigo %>" required>
        </div>
        <div class="mb-3">
          <label class="form-label">Nombre</label>
          <input class="form-control" type="text" name="txtNombre" maxlength="100" value="<%= nombre %>" required>
        </div>
        <div class="mb-3">
          <label class="form-label">Nivel</label>
          <select class="form-select" name="txtNivel" required>
            <% for (String[] n : niveles) { %>
              <option value="<%= n[0] %>" <%= n[0].equals(nivel) ? "selected" : "" %>><%= n[1] %></option>
            <% } %>
          </select>
        </div>
        <div class="mb-3">
          <label class="form-label">Docente</label>
          <select class="form-select" name="txtDocente" required>
            <option value="">-- Seleccione --</option>
            <% if (docentes != null) { for (Docente d : docentes) { %>
              <option value="<%= d.getIdDocente() %>" <%= d.getIdDocente() == idDocente ? "selected" : "" %>><%= d.getNombreCompleto() %></option>
            <% } } %>
          </select>
        </div>

        <button type="submit" class="btn btn-primary"><%= editando ? "Actualizar" : "Registrar" %></button>
        <a href="CursoServlet?type=list" class="btn btn-warning"><%= editando ? "Cancelar" : "Listar" %></a>
      </form>
    </div>

    <div class="col-lg-8">
      <h3>Cursos Disponibles</h3>
      <table class="table table-hover align-middle">
        <thead>
          <tr>
            <th>Id</th><th>Código</th><th>Nombre</th><th>Nivel</th><th>Docente</th><th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          <% if (data == null) { %>
            <tr>
              <td colspan="6" class="text-danger">
                Abre esta página desde el Servlet: <a href="CursoServlet?type=list">CursoServlet?type=list</a>
              </td>
            </tr>
          <% } else if (data.isEmpty()) { %>
            <tr>
              <td colspan="6" class="text-muted">No hay cursos registrados</td>
            </tr>
          <% } else { for (Curso item : data) { %>
            <tr>
              <td><%= item.getIdCurso() %></td>
              <td><%= item.getCodigo() %></td>
              <td><%= item.getNombre() %></td>
              <td><%= item.getNivel() %></td>
              <td><%= item.getDocente().getNombreCompleto() %></td>
              <td class="text-nowrap">
                <a href="CursoServlet?type=get&id=<%= item.getIdCurso() %>" class="btn btn-sm btn-outline-warning" title="Editar">
                  <i class="bi bi-pencil"></i>
                </a>
                <a href="CursoServlet?type=delete&id=<%= item.getIdCurso() %>" class="btn btn-sm btn-outline-danger" title="Eliminar"
                   onclick="return confirm('¿Seguro que desea eliminar este curso?');">
                  <i class="bi bi-trash"></i>
                </a>
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
