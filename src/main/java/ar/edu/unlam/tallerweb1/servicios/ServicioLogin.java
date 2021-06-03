package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.DatosRegistroPaciente;

// Interface que define los metodos del Servicio de Usuarios.
public interface ServicioLogin {

	Usuario consultarUsuario(Usuario usuario);

	Usuario consultarUsuarioEmail(String email);

	void createUsuario(Usuario usuario);

    void registrarPaciente(DatosRegistroPaciente datosRegistroPaciente);
}
