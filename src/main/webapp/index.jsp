<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Cibertec - Principal</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css">
</head>
<body class="container mt-4">

	<%@ include file="/WEB-INF/menu.jspf"%>
	<main>
		<div class="row g-3 mb-4">
			<div class="col-md-6">
				<div class="card h-100 shadow-sm">
					<div class="card-body">
						<h5 class="card-title">Cursos</h5>
						<p class="card-text">Registrar, listar, actualizar y eliminar
							cursos.</p>
						<a href="CursoServlet?type=list" class="btn btn-primary">Ir a
							Cursos</a>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="card h-100 shadow-sm">
					<div class="card-body">
						<h5 class="card-title">Docentes</h5>
						<p class="card-text">Registrar, listar, actualizar y eliminar
							docentes.</p>
						<a href="DocenteServlet?type=list" class="btn btn-primary">Ir
							a Docentes</a>
					</div>
				</div>
			</div>
		</div>
		<%-- Acordeón: secciones que se abren y cierran --%>
		<section class="accordion" id="noticias">

			<article class="accordion-item">
				<h2 class="accordion-header">
					<button class="accordion-button" type="button"
						data-bs-toggle="collapse" data-bs-target="#principales">
						Principales Noticias</button>
				</h2>
				<div id="principales" class="accordion-collapse collapse show">
					<div class="accordion-body">Lorem Ipsum is simply dummy text
						of the printing and typesetting industry. Lorem Ipsum has been the
						industry's standard dummy text ever since the 1500s, when an
						unknown printer took a galley of type and scrambled it to make a
						type specimen book. It has survived not only five centuries.</div>
				</div>
			</article>

			<article class="accordion-item">
				<h2 class="accordion-header">
					<button class="accordion-button" type="button"
						data-bs-toggle="collapse" data-bs-target="#ultimas">
						Últimas Noticias</button>
				</h2>
				<div id="ultimas" class="accordion-collapse collapse show">
					<div class="accordion-body">Lorem Ipsum is simply dummy text
						of the printing and typesetting industry. Lorem Ipsum has been the
						industry's standard dummy text ever since the 1500s, when an
						unknown printer took a galley of type and scrambled it to make a
						type specimen book. It has survived not only five centuries.</div>
				</div>
			</article>

		</section>
	</main>
	<footer class="card text-center mt-4 mb-4">
		<div class="card-header">Footer</div>
		<div class="card-body">
			<h5 class="card-title">Cibertec</h5>
			<p class="card-text">Creado y soportado por Cibertec</p>
			<a href="#" class="btn btn-primary">Contáctanos</a>
		</div>
		<div class="card-footer text-body-secondary">2023</div>
	</footer>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>