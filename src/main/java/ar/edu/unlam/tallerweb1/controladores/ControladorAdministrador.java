package ar.edu.unlam.tallerweb1.controladores;


import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioPersonaMedico;
import ar.edu.unlam.tallerweb1.servicios.ServicioAdministrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioPersona;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


@Controller
@RequestMapping("/administrador")
public class ControladorAdministrador {


    private ServicioAdministrador servicioAdministrador;

    @Autowired
    public ControladorAdministrador(ServicioAdministrador servicioAdministrador) {
        this.servicioAdministrador = servicioAdministrador;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, false));
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAHomeAdministrador() {
        return new ModelAndView("home/home-administrador");
    }

    //laburamos este
    @RequestMapping(path = "/registrar_persona", method = RequestMethod.GET)
    public ModelAndView irARegistrarPersona() {
        FormularioPersona persona = new FormularioPersona();
        ModelMap model = new ModelMap();
        model.put("persona", persona);
        return new ModelAndView("administrador/registrar-persona", model);
    }

    @RequestMapping(path = "/registrar_persona_medico", method = RequestMethod.GET)
    public ModelAndView irARegistrarPersonaMedico() {
        FormularioPersonaMedico persona = new FormularioPersonaMedico();
        ModelMap model = new ModelMap();
        model.put("persona", persona);
        return new ModelAndView("administrador/registrar-persona-medico", model);
    }

    @RequestMapping(path = "/registrar_persona_medico", method = RequestMethod.POST)
    public ModelAndView registrarPersonaMedico(@Valid FormularioPersonaMedico persona, BindingResult result) {
        ModelMap model = new ModelMap();
        model.put("persona", persona);
        ArrayList<String> errores = new ArrayList();
        Persona nuevaPersona = persona.toPersona();

        try {
            servicioAdministrador.registrarMedico(nuevaPersona);
            model.put("persona", persona);
            model.put("exito", "La persona se registró correctamente");
        } catch (RuntimeException | ParseException e) {
            errores.add(e.getMessage());
            model.put("persona", persona);
            model.put("errores", errores);
            return new ModelAndView("administrador/registrar-persona-medico", model);
        }

            return new ModelAndView("administrador/registrar-persona-medico", model);
    }

    @RequestMapping(path = "/registrar_persona", method = RequestMethod.POST)
    public ModelAndView registrarPersona(@Valid FormularioPersona persona, BindingResult result) {
        ModelMap model = new ModelMap();
        model.put("persona", persona);
        ArrayList<String> errores = new ArrayList();
        Persona nuevaPersona = persona.toPersona();


        try {
            servicioAdministrador.registrar(nuevaPersona);
            model.put("persona", persona);
            model.put("exito", "La persona se registró correctamente");
        } catch (RuntimeException | ParseException e) {
            errores.add(e.getMessage());
            model.put("persona", persona);
            model.put("errores", errores);
            return new ModelAndView("administrador/registrar-persona", model);
        }


        return new ModelAndView("administrador/registrar-persona", model);
    }


}