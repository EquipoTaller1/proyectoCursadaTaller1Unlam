package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroPaciente;
import org.springframework.ui.ModelMap;
import org.springframework.validation.FieldError;

import java.util.List;

// Interface que define los metodos del Servicio de Usuarios.
public interface ServicioLogin {

	Usuario consultarUsuario(Usuario usuario);

	Usuario consultarUsuarioEmail(String email);

	void createUsuario(Usuario usuario);

    void registrarPaciente(FormularioRegistroPaciente formularioRegistroPaciente, List<FieldError> list);
}
