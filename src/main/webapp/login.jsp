<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Sistema de Cursos - Iniciar sesión</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">

	<div class="container">
		<div class="row justify-content-center align-items-center vh-100">
			<div class="col-sm-8 col-md-6 col-lg-4">
				<div class="card shadow">
					<div class="card-body p-4">

						<h4 class="text-center mb-1">Sistema de Cursos</h4>
						<p class="text-center text-muted mb-4">Ingrese sus credenciales</p>

						<div class="alert alert-danger ${empty mensaje ? 'd-none' : ''}">${mensaje}</div>
						<div class="alert alert-success ${empty param.salir ? 'd-none' : ''}">Sesión cerrada correctamente.</div>

						<form action="LoginServlet" method="post">
							<input type="hidden" name="type" value="login">

							<div class="mb-3">
								<label for="txtUsuario" class="form-label">Usuario</label>
								<input type="text" class="form-control" id="txtUsuario" name="txtUsuario"
									maxlength="30" required autofocus value="${param.txtUsuario}">
							</div>

							<div class="mb-3">
								<label for="txtClave" class="form-label">Clave</label>
								<input type="password" class="form-control" id="txtClave" name="txtClave"
									maxlength="50" required>
							</div>

							<button type="submit" class="btn btn-primary w-100">Ingresar</button>
						</form>

					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>