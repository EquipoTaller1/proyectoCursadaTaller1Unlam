package ar.edu.unlam.tallerweb1.controladores;

//        import ar.edu.unlam.tallerweb1.servicios.ServicioPaciente;

import ar.edu.unlam.tallerweb1.Excepciones.AfiliadoNoExisteException;
import ar.edu.unlam.tallerweb1.Excepciones.ErrorEnFormatoDeFechaException;
import ar.edu.unlam.tallerweb1.Excepciones.FaltanDatosParaElRegistroException;
import ar.edu.unlam.tallerweb1.Excepciones.PersonaYaExisteException;
import ar.edu.unlam.tallerweb1.configuraciones.SendEmail;
import ar.edu.unlam.tallerweb1.modelo.Persona;
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

    @RequestMapping(path = "/registrar_persona", method = RequestMethod.POST)
    public ModelAndView registrarPersona(@Valid FormularioPersona formulario, BindingResult result) {
        ModelMap model = new ModelMap();
        FormularioPersona persona = new FormularioPersona();
        ArrayList<String> errores = new ArrayList();
        Persona nuevaPersona = formulario.toPersona();


        try {
            servicioAdministrador.registrar(nuevaPersona);
        } catch (FaltanDatosParaElRegistroException e) {
            errores.add("Complete todos los datos para el registro");
        } catch (PersonaYaExisteException e) {
            errores.add("La persona ya está registrada");
        } catch (ErrorEnFormatoDeFechaException e) {
            errores.add("La fecha de nacimiento es incorrecta");
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (errores.isEmpty()) {

            model.put("persona", persona);
            model.put("exito", "La persona se registró correctamente");

            servicioAdministrador.enviarEmailDeRegistro(formulario);

        } else {
            model.put("persona", formulario);
            model.put("errores", errores);
        }
        return new ModelAndView("administrador/registrar-persona", model);
    }


}