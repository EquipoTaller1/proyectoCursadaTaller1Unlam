<%--
  Created by IntelliJ IDEA.
  User: enzo-
  Date: 24/4/2021
  Time: 17:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!-- Navigation -->
<ul class="navbar-nav">
	<sec:authorize access="hasRole('Medico')">
		<li class="nav-item"><a class="nav-link"
								href="${pageContext.request.contextPath}/medico/home"> <i
				class="ni ni-tv-2 text-primary"></i> Home
		</a></li>
		<li class="nav-item"><a class="nav-link"
								href="${pageContext.request.contextPath}/medico/mapa"> <i
				class="ni ni-pin-3 text-orange"></i> Maps
		</a></li>
		<li class="nav-item"><a class="nav-link"
								href="${pageContext.request.contextPath}/medico/citas-del-dia"> <i
				class="ni ni-calendar-grid-58 text-green"></i> Mis citas para hoy
		</a></li>
		<li class="nav-item"><a class="nav-link"
								href="${pageContext.request.contextPath}/medico/agregar-especialidad"> <i
				class="ni ni-bullet-list-67 text-blue"></i> Mis especialidades
		</a></li>
		<li class="nav-item"><a class="nav-link"
								href="${pageContext.request.contextPath}/medico/mi-agenda"> <i
				class="ni ni-bullet-list-67 text-blue"></i> Mi agenda
		</a></li>

	</sec:authorize>
	<sec:authorize access="hasRole('Paciente')">
		<li class="nav-item"><a class="nav-link"
								href="${pageContext.request.contextPath}/paciente/home">
			<i class="ni ni-tv-2 text-primary"></i> Home
		</a></li>
		<li class="nav-item"><a class="nav-link"
								href="${pageContext.request.contextPath}/paciente/citas/index">
			<i class="ni ni-calendar-grid-58"></i> Mis citas
		</a></li>
		<li class="nav-item"><a class="nav-link"
								href="${pageContext.request.contextPath}/paciente/mapa"> <i
				class="ni ni-pin-3 text-orange"></i> Maps
		</a></li>
	</sec:authorize>
	<sec:authorize access="hasRole('Administrador')">
	<li class="nav-item"><a class="nav-link"
							href="${pageContext.request.contextPath}/administrador/home">
		<i class="ni ni-tv-2 text-primary"></i> Home
	</a></li>
	<li class="nav-item"><a class="nav-link"
							href="${pageContext.request.contextPath}/administrador/registrar_persona">
		<i class="ni ni-badge"></i> Registrar Persona
	</a></li>
		<li class="nav-item"><a class="nav-link"
								href="${pageContext.request.contextPath}/administrador/registrar_persona_medico">
			<i class="ni ni-badge"></i> Registrar Medico
		</a></li>
	</sec:authorize>
	<%--<li class="nav-item"><a class="nav-link"
		href="../examples/icons.html"> <i class="ni ni-planet text-blue"></i>
			Icons
	</a></li>

	<li class="nav-item"><a class="nav-link"
		href="../examples/profile.html"> <i
			class="ni ni-single-02 text-yellow"></i> User profile
	</a></li>
	<li class="nav-item"><a class="nav-link active"
		href="../examples/tables.html"> <i
			class="ni ni-bullet-list-67 text-red"></i> Tables
	</a></li>
	<li class="nav-item"><a class="nav-link"
		href="../examples/login.html"> <i class="ni ni-key-25 text-info"></i>
			Login
	</a></li>
	<li class="nav-item"><a class="nav-link"
		href="../examples/register.html"> <i
			class="ni ni-circle-08 text-pink"></i> Register
	</a></li>
</ul>
<!-- Divider -->
<hr class="my-3">
<!-- Heading -->
<h6 class="navbar-heading text-muted">Documentation</h6>
<!-- Navigation -->
<ul class="navbar-nav mb-md-3">
	<li class="nav-item"><a class="nav-link"
		href="https://demos.creative-tim.com/argon-dashboard/docs/getting-started/overview.html">
			<i class="ni ni-spaceship"></i> Getting started
	</a></li>
	<li class="nav-item"><a class="nav-link"
		href="https://demos.creative-tim.com/argon-dashboard/docs/foundation/colors.html">
			<i class="ni ni-palette"></i> Foundation
	</a></li>
	<li class="nav-item"><a class="nav-link"
		href="https://demos.creative-tim.com/argon-dashboard/docs/components/alerts.html">
			<i class="ni ni-ui-04"></i> Components
	</a></li>--%>
</ul>
