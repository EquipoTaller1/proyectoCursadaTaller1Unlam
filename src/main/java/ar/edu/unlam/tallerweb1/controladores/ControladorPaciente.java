package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Cita;
import ar.edu.unlam.tallerweb1.servicios.ServicioPaciente;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/paciente")
public class ControladorPaciente {

    private ServicioPaciente servicioPaciente;

    @Autowired
    public  ControladorPaciente(ServicioPaciente servicioPaciente){
        this.servicioPaciente = servicioPaciente;
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAHomePaciente(Authentication authentication)
    {
        ModelMap model = new ModelMap();
        User user = (User) authentication.getPrincipal();
        model.put("citas", servicioPaciente.getCitasProximas(user.getUsername()));

        return new ModelAndView("home/home-paciente", model);
    }

    @RequestMapping("/mapa")
    public ModelAndView mapaPaciente(){

        return new ModelAndView("maps/mapaPaciente");
    }

    @RequestMapping("/citas/index")
    public ModelAndView irAMisCitas(Authentication authentication) {
        ModelMap model = new ModelMap();
        User user = (User) authentication.getPrincipal();
        model.put("citas", servicioPaciente.getCitas(user.getUsername()));

        return new ModelAndView("mis-citas/index", model);
    }
}
