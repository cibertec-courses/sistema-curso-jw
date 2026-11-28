<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Mis datos</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css">
</head>

<body class="container mt-4">

	<%@ include file="/WEB-INF/menu.jspf"%>

	<div class="row justify-content-center">
		<div class="col-lg-6">
			<div class="card shadow-sm">
				<div class="card-body p-4">

					<h3 class="mb-3">Mis datos</h3>

					<div class="alert alert-success ${empty exito ? 'd-none' : ''}">${exito}</div>
					<div class="alert alert-danger ${empty mensaje ? 'd-none' : ''}">${mensaje}</div>

					<form action="UsuarioServlet" method="post">
						<input type="hidden" name="type" value="actualizar">

						<div class="row">
							<div class="col-md-6 mb-3">
								<label class="form-label">Usuario</label>
								<input type="text" class="form-control" value="${sessionScope.usuarioLogueado.usuario}" disabled>
							</div>
							<div class="col-md-6 mb-3">
								<label class="form-label">Rol</label>
								<input type="text" class="form-control" value="${sessionScope.usuarioLogueado.rol.nombre}" disabled>
							</div>
						</div>

						<div class="mb-3">
							<label for="txtNombres" class="form-label">Nombres</label>
							<input type="text" class="form-control" id="txtNombres" name="txtNombres" maxlength="50" required
								value="${empty param.txtNombres ? sessionScope.usuarioLogueado.nombres : param.txtNombres}">
						</div>

						<div class="mb-3">
							<label for="txtApellidos" class="form-label">Apellidos</label>
							<input type="text" class="form-control" id="txtApellidos" name="txtApellidos" maxlength="50" required
								value="${empty param.txtApellidos ? sessionScope.usuarioLogueado.apellidos : param.txtApellidos}">
						</div>

						<div class="mb-3">
							<label for="txtCorreo" class="form-label">Correo</label>
							<input type="email" class="form-control" id="txtCorreo" name="txtCorreo" maxlength="80" required
								value="${empty param.txtCorreo ? sessionScope.usuarioLogueado.correo : param.txtCorreo}">
						</div>

						<p class="text-muted small">Último acceso: ${sessionScope.usuarioLogueado.ultimoAccesoTexto}</p>

						<button type="submit" class="btn btn-primary">Guardar cambios</button>
						<a href="index.jsp" class="btn btn-secondary">Volver</a>
					</form>

				</div>
			</div>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
