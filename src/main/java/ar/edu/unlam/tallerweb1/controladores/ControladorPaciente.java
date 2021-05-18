package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.servicios.ServicioMapa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/paciente")
public class ControladorPaciente {

    private ServicioMapa servicioMapa;

    @Autowired
    public  ControladorPaciente(ServicioMapa servicioMapa){ this.servicioMapa = servicioMapa;}

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAHomePaciente()
    {
        return new ModelAndView("home/home-paciente");
    }

    @RequestMapping("/mapa")
    public ModelAndView mapaPaciente(){

        return new ModelAndView("maps/mapaPaciente");
    }
}
