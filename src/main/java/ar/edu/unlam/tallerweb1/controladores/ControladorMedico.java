package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.servicios.ServicioMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/medico")
public class ControladorMedico {

    private ServicioMedico servicioMedico;

    @Autowired
    public ControladorMedico(ServicioMedico servicioMedico) {
        this.servicioMedico = servicioMedico;
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAHomeMedico()
    {
        return new ModelAndView("home/home-medico");
    }

    @RequestMapping("/mapa")
    public ModelAndView mapaMedico(){

        return new ModelAndView("maps/mapaMedico");
    }
}
