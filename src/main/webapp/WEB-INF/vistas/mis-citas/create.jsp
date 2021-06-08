<%--
  Created by IntelliJ IDEA.
  User: enzo-
  Date: 7/6/2021
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:layout>
    <jsp:attribute name="title">
        Mis citas
    </jsp:attribute>
    <jsp:attribute name="script">
        <script src="${pageContext.request.contextPath}/js/citas/createCita.js"></script>
    </jsp:attribute>
    <jsp:body>
        <!-- Table -->
        <div class="row">
            <div class="col">
                <div class="card shadow">
                    <div class="card-header border-0">
                        <h3 class="mb-0">Nueva cita</h3>
                    </div>
                    <div class="card-body">
                        <div class="col-12">
                            <c:if test="${not empty errores}">
                                <div class="alert alert-danger" role="alert">
                                    <p>Corrigue los siguientes errores:</p>
                                    <ul>
                                        <c:forEach items="${errores}" var="error">
                                            <li>${error}</li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:if>
                        </div>
                        <form:form action="${pageContext.request.contextPath}/paciente/citas/store" method="post" modelAttribute="datos">
                            <div class="form-group">
                                <label for="selectEspecialidad">Especialidad</label>
                                <form:select path="especialidad" id="selectEspecialidad" cssClass="form-control">
                                    <option value="" disabled selected>Seleccione una especialidad</option>
                                    <form:options items="${especialidades}" itemLabel="descripcion" itemValue="id" />
                                </form:select>
                            </div>
                            <div class="form-group">
                                <label for="selectMedico" class="form-control-label">Medico</label>
                                <form:select path="medico" id="selectMedico" cssClass="form-control">
                                    <option value="" selected disabled>Seleccione un medico</option>
                                </form:select>
                            </div>
                            <div class="form-group">
                                <label for="fecha" class="form-control-label">Fecha</label>
                                <form:input path="fecha" type="date" id="fecha" cssClass="form-control" />
                            </div>
                            <div class="form-group">
                                <label for="hora" class="form-control-label">Hora</label>
                                <form:input path="hora" cssClass="form-control" type="time" />
                            </div>

                            <button class="btn btn-primary" type="submit">
                                Registrar cita
                            </button>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:layout>
