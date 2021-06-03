package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.servicios.ServicioRegistroUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorLogin {

	// La anotacion @Autowired indica a Spring que se debe utilizar el contructor como mecanismo de inyección de dependencias,
	// es decir, qeue lo parametros del mismo deben ser un bean de spring y el framewrok automaticamente pasa como parametro
	// el bean correspondiente, en este caso, un objeto de una clase que implemente la interface ServicioLogin,
	// dicha clase debe estar anotada como @Service o @Repository y debe estar en un paquete de los indicados en
	// applicationContext.xml
	private ServicioRegistroUsuario servicioRegistroUsuario;

	@Autowired
	public ControladorLogin(ServicioRegistroUsuario servicioRegistroUsuario){
		this.servicioRegistroUsuario = servicioRegistroUsuario;
	}

	// Escucha la url /, y redirige a la URL /login, es lo mismo que si se invoca la url /login directamente.
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView inicio() {
		return new ModelAndView("redirect:/login");
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginPage(@RequestParam(value = "error", required = false) String error,
								  @RequestParam(value = "logout", required = false) String logout,
								  @RequestParam(value = "exito", required = false) String exito) {

		ModelMap model = new ModelMap();
		if (error != null) {
			model.put("error", "Usuario o clave incorrecta");
		}
		if (logout != null) {
			model.put("message", "Cerraste sesion correctamente");
		}
		if (exito != null){
			model.put("message", "Registro existoso");
		}

		return new ModelAndView("auth/login", model);
	}
}
