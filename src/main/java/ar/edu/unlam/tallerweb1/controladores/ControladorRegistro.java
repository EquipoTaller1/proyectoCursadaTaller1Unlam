package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroPaciente;
import ar.edu.unlam.tallerweb1.servicios.ServicioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

@Controller
public class ControladorRegistro {

    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorRegistro(ServicioLogin servicioLogin){
        this.servicioLogin = servicioLogin;
    }

    @RequestMapping("/registro")
    public ModelAndView irARegistro(@RequestParam(value = "message", required = false) String message){
        ModelMap model = new ModelMap();
        if (message != null){
            model.put("message", message);
        }

        model.put("formularioPaciente", new FormularioRegistroPaciente());

        return new ModelAndView("auth/register", model);
    }

    @RequestMapping("/registro/store")
    public ModelAndView store(@Valid FormularioRegistroPaciente formulario, BindingResult result){
        ModelMap model = servicioLogin.registrarPaciente(formulario, result.getFieldErrors());

        if (model.containsKey("errores")){
            model.put("formularioPaciente", formulario);
            return new ModelAndView("auth/register", model);
        }

        return new ModelAndView("redirect:/login?exito", model);
    }

}
