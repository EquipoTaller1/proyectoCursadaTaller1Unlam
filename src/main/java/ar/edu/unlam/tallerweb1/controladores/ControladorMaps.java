package ar.edu.unlam.tallerweb1.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorMaps {

    @RequestMapping("/mapaPaciente")
    public ModelAndView mapaPaciente(){

        return new ModelAndView("maps/mapaPaciente");
    }
    
    @RequestMapping("/mapaMedico")
    public ModelAndView mapaMedico(){

        return new ModelAndView("maps/mapaMedico");
    }
}
