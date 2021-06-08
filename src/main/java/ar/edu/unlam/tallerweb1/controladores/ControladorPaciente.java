package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.formularios.DatosCita;
import ar.edu.unlam.tallerweb1.servicios.ServicioCita;
import ar.edu.unlam.tallerweb1.servicios.ServicioPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/paciente")
public class ControladorPaciente {

    private ServicioPaciente servicioPaciente;
    private ServicioCita servicioCita;

    @Autowired
    public  ControladorPaciente(ServicioPaciente servicioPaciente, ServicioCita servicioCita){
        this.servicioPaciente = servicioPaciente;
        this.servicioCita = servicioCita;
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

    @RequestMapping("/citas/create")
    public ModelAndView irACrearCita(){
        ModelMap model = new ModelMap();
        model.put("datos", new DatosCita());
        model.put("especialidades", servicioCita.allEspecialidades());

        return new ModelAndView("mis-citas/create", model);
    }

    @RequestMapping(value = "/citas/store", method = RequestMethod.POST)
    public ModelAndView createCita(@Valid DatosCita datosCita, BindingResult result, Authentication authentication){
        ModelMap model = new ModelMap();
        List<String> errores = new ArrayList<>();
        User user = (User) authentication.getPrincipal();
        datosCita.setPaciente(user.getUsername());
        datosCita.setTipoCita(1);

        if (result.hasErrors()){
            result.getFieldErrors().forEach(error -> {
                errores.add(error.getDefaultMessage());
            });

            model.put("errores", errores);
            return new ModelAndView("redirect:/paciente/citas/create", model);
        }

        try {
            servicioCita.create(datosCita);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/paciente/citas/index");
    }
}
