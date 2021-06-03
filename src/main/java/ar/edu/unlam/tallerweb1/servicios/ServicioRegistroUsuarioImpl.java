package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.Excepciones.*;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroMedico;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroPaciente;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioMedico;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPaciente;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioAdministrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.repositorios.RepositorioRegistroUsuario;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import org.springframework.ui.ModelMap;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

// Implelemtacion del Servicio de usuarios, la anotacion @Service indica a Spring que esta clase es un componente que debe
// ser manejado por el framework, debe indicarse en applicationContext que busque en el paquete ar.edu.unlam.tallerweb1.servicios
// para encontrar esta clase.
// La anotacion @Transactional indica que se debe iniciar una transaccion de base de datos ante la invocacion de cada metodo del servicio,
// dicha transaccion esta asociada al transaction manager definido en el archivo spring-servlet.xml y el mismo asociado al session factory definido
// en hibernateCOntext.xml. De esta manera todos los metodos de cualquier dao invocados dentro de un servicio se ejecutan en la misma transaccion
@Service()
@Transactional
public class ServicioRegistroUsuarioImpl implements ServicioRegistroUsuario {

	private RepositorioRegistroUsuario repositorioRegistroUsuario;
	private RepositorioAdministrador repositorioAdministrador;
	private RepositorioPaciente repositorioPaciente;
	private RepositorioMedico repositorioMedico;

	@Autowired
	public ServicioRegistroUsuarioImpl(RepositorioRegistroUsuario repositorioRegistroUsuario, RepositorioAdministrador repositorioAdministrador, RepositorioPaciente repositorioPaciente, RepositorioMedico repositorioMedico){
		this.repositorioRegistroUsuario = repositorioRegistroUsuario;
		this.repositorioAdministrador = repositorioAdministrador;
		this.repositorioPaciente = repositorioPaciente;
		this.repositorioMedico = repositorioMedico;
	}

	@Override
	public Usuario consultarUsuario (Usuario usuario) {
		return repositorioRegistroUsuario.consultarUsuario(usuario);
	}

	@Override
	public Usuario consultarUsuarioEmail(String email) {
		return repositorioRegistroUsuario.userByEmail(email);
	}

	@Override
	public void createUsuario(Usuario usuario) {
		repositorioRegistroUsuario.createUser(usuario);
	}

	@Override
	public ModelMap registrarPaciente(FormularioRegistroPaciente formulario, List<FieldError> result) {
		ModelMap model = new ModelMap();
		ArrayList<String> errores = new ArrayList();

		if (!result.isEmpty()){
			result.forEach(error -> {
				errores.add(error.getDefaultMessage());
			});

			throw new FormularioRegistroPacienteException(errores);
		}
		else {
			if (!formulario.getPassword().equals(formulario.getPasswordRepet())){
				throw new ContraseniasNoCoincidenException();
			}

			if (this.consultarUsuarioEmail(formulario.getEmail()) != null){
				throw new EmailEnUsoException();
			}

			Persona persona = this.repositorioPaciente.consultarAfiliado(formulario.getAfiliado());
			if (persona == null){
				throw new AfiliadoNoExisteException();
			}
			else if (persona.getUsuario() != null){
				throw new AfiliadoRegistradoException();
			}

			this.repositorioPaciente.registrarPaciente(formulario, persona);
			model.put("exito", "El usuario se creo correctamente");
		}

		return  model;
	}

	@Override
	public ModelMap registrarMedico(FormularioRegistroMedico formulario, List<FieldError> result) throws MedicoNoExisteException {
		ModelMap model = new ModelMap();
		ArrayList<String> errores = new ArrayList();

		if (!result.isEmpty()){
			result.forEach(error -> {
				errores.add(error.getDefaultMessage());
			});

			throw new FormularioRegistroMedicoException(errores);
		}
		else {
			if (!formulario.getPassword().equals(formulario.getPasswordRepet())){
				throw new ContraseniasNoCoincidenException();
			}

			if (this.consultarUsuarioEmail(formulario.getEmail()) != null){
				throw new EmailEnUsoException();
			}

			Persona persona = this.repositorioMedico.consultarMedico(formulario.getMatricula());
			if (persona == null){
				throw new MedicoNoExisteException();
			}
			else if (persona.getUsuario() != null){
				throw new MedicoYaRegistradoException();
			}

			this.repositorioMedico.registrarMedico(formulario, persona);
			model.put("exito", "El usuario se creo correctamente");
		}

		return  model;
	}



}
