package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.Excepciones.MedicoNoExisteException;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroMedico;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroPaciente;
import org.springframework.ui.ModelMap;
import org.springframework.validation.FieldError;

import java.util.List;

// Interface que define los metodos del Servicio de Usuarios.
public interface ServicioRegistroUsuario {

	Usuario consultarUsuario(Usuario usuario);

	Usuario consultarUsuarioEmail(String email);

	void createUsuario(Usuario usuario);

    ModelMap registrarPaciente(FormularioRegistroPaciente formularioRegistroPaciente, List<FieldError> list);



	ModelMap registrarMedico(FormularioRegistroMedico formulario, List<FieldError> fieldErrors) throws MedicoNoExisteException;
}
