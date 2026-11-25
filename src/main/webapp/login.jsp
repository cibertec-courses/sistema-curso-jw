<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("UTF-8");
	String rol = request.getParameter("rol");
	String credencial = request.getParameter("credencial");
	
%>    
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Validacion de Accesos</title>
</head>
<body>
	<form action="login.jsp" method="post">
		<table border="1" aling="center">
			<tr>
				<th colspan="2"> Validacion de Accesos</th>
			</tr>
			<tr>
				<td> Rol de Usuario: </td>
				<td>
					<select name="rol">
						<option> Administrador </option>
						<option> Docente </option>
						<option> Alumno </option>
					</select> 
				</td>
			</tr>
			<tr>
				<td> Credencial</td>
				<td> <input type="password" name="credencial"> </td>
			</tr>
			<tr>
				<td colspan="2" aling="center"> <input type="submit" value="Enviar"/></td>
			</tr>
		</table>
	</form>
	
	<%-- Solo mostramos mensaje si ya se envio el mensaje el formulario (rol != null) --%>
	<% if (rol != null){ %>
		<p align="center">
			<% if (credencial == null || credencial.isEmpty() ){ %>
				<span style="color:red"> Debe ingresar su credencial. </span>
			<% } else{ %>
				<span style="color:green"> Ingreso como <strong> <%= rol %></strong> </span>
			<%} %>
		</p>
	<%} %>
</body>
</html>