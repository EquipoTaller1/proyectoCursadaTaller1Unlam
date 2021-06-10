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
        Mis especialidades
    </jsp:attribute>

    <jsp:body>
          <!-- Table -->
          <div class="row">
              <div class="col">
                  <div class="card shadow">
                      <div class="card-header border-0">
                          <h3 class="mb-0">Nueva especialidad</h3>
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
                          <form:form action="${pageContext.request.contextPath}/medico/agregar-especialidad" method="post" modelAttribute="datos">
                              <div class="form-group">
                                  <label for="especialidadNueva">Especialidad</label>
                                  <form:select path="especialidadNueva" id="especialidadNueva" cssClass="form-control">
                                      <option value="" disabled selected>Seleccione una especialidad</option>
                                      <form:options items="${especialidadesTodas}" itemLabel="descripcion" itemValue="id" />
                                  </form:select>
                              </div>
                              <button class="btn btn-primary" type="submit">
                                  Agregar Especialidad
                              </button>
                          </form:form>
                      </div>
                  </div>
              </div>
          </div>

        <!-- Table -->
        <div class="row">
            <div class="col">
                <div class="card shadow">
                    <div class="card-header border-0">
                        <h3 class="mb-0">Mis especialidades</h3>
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty datos.usuario.especialidades}">
                                <c:forEach items="${datos.usuario.especialidades}" var="especialidad">
                                    <div class="col-12 col-md-8 col-lg-10 mb-2">
                                        <div class="card card-stats mb-4 mb-xl-0">
                                            <div class="card-body">
                                                <div class="row">
                                                    <div class="col">
                                                        <h3 class="card-title text-uppercase mb-0">${especialidad.descripcion}</h3>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="alert alert-warning" role="alert">
                                    No tienes especialidaddes.
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>

    </jsp:body>
  </t:layout>
