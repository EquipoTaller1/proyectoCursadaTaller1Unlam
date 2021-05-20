package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroPaciente;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPaciente;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPersona;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuario;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Implelemtacion del Servicio de usuarios, la anotacion @Service indica a Spring que esta clase es un componente que debe
// ser manejado por el framework, debe indicarse en applicationContext que busque en el paquete ar.edu.unlam.tallerweb1.servicios
// para encontrar esta clase.
// La anotacion @Transactional indica que se debe iniciar una transaccion de base de datos ante la invocacion de cada metodo del servicio,
// dicha transaccion esta asociada al transaction manager definido en el archivo spring-servlet.xml y el mismo asociado al session factory definido
// en hibernateCOntext.xml. De esta manera todos los metodos de cualquier dao invocados dentro de un servicio se ejecutan en la misma transaccion
@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

	private RepositorioUsuario servicioLoginDao;
	private RepositorioPersona repositorioPersona;
	private RepositorioPaciente repositorioPaciente;

	@Autowired
	public ServicioLoginImpl(RepositorioUsuario servicioLoginDao, RepositorioPersona repositorioPersona, RepositorioPaciente repositorioPaciente){
		this.servicioLoginDao = servicioLoginDao;
		this.repositorioPersona = repositorioPersona;
		this.repositorioPaciente = repositorioPaciente;
	}

	@Override
	public Usuario consultarUsuario (Usuario usuario) {
		return servicioLoginDao.consultarUsuario(usuario);
	}

	@Override
	public Usuario consultarUsuarioEmail(String email) {
		return servicioLoginDao.userByEmail(email);
	}

	@Override
	public void createUsuario(Usuario usuario) {
		servicioLoginDao.createUser(usuario);
	}

	@Override
	public ModelMap registrarPaciente(FormularioRegistroPaciente formulario, List<FieldError> result) {
		ModelMap model = new ModelMap();
		ArrayList<String> errores = new ArrayList();

		if (!result.isEmpty()){
			result.forEach(error -> {
				errores.add(error.getDefaultMessage());
			});
			model.put("errores", errores);
		}
		else {
			if (!formulario.getPassword().equals(formulario.getPasswordRepet())){
				errores.add("Las contraseñas no coinciden");
				model.put("errores", errores);
				return model;
			}

			if (this.consultarUsuarioEmail(formulario.getEmail()) != null){
				errores.add("El email ya se encuentra registrado");
				model.put("errores", errores);
				return model;
			}

			Persona persona = this.repositorioPersona.consultarAfiliado(formulario.getAfiliado());
			if (persona == null){
				errores.add("El numero de afiliado no es correcto");
				model.put("errores", errores);
			}
			else if (persona.getUsuario() != null){
				errores.add("El numero de afiliado ya se encuentra registrado");
				model.put("errores", errores);
			}

			if (errores.isEmpty()){
				this.repositorioPaciente.registrarPaciente(formulario, persona);
				model.put("exito", "El usuario se creo correctamente");
			}
		}

		return  model;
	}

}
