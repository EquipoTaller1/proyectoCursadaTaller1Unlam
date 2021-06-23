package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.Excepciones.EspecialidadRepetida;
import ar.edu.unlam.tallerweb1.modelo.Ubicacion;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioEspecialidades;
import ar.edu.unlam.tallerweb1.servicios.ServicioCita;
import ar.edu.unlam.tallerweb1.servicios.ServicioMedico;
import ar.edu.unlam.tallerweb1.servicios.ServicioPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/medico")
public class ControladorMedico {

    private ServicioMedico servicioMedico;
    private ServicioCita servicioCita;




    @Autowired
    public ControladorMedico(ServicioMedico servicioMedico, ServicioCita servicioCita) {
        this.servicioMedico = servicioMedico;
        this.servicioCita = servicioCita;

    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAHomeMedico()
    {
        return new ModelAndView("home/home-medico");
    }



    @RequestMapping(value = "/mapa/{id}", method = RequestMethod.POST)
    public ModelAndView mapaMedico(@PathVariable String id){

        String idEnString = id.substring(3);

        Long idEnLong = Long.parseLong(idEnString);

        Usuario usuario = servicioCita.userById(idEnLong);

        String latitud = usuario.getUbicacion().getLat_actual();
        String longitud = usuario.getUbicacion().getLong_actual();

        ModelMap model = new ModelMap();
        model.put("latitud", latitud);
        model.put("longitud", longitud);




        return new ModelAndView("maps/mapaMedico", model);
    }


    @RequestMapping("/citas-del-dia")
    public ModelAndView irAMisCitas(Authentication authentication) {
        ModelMap model = new ModelMap();
        User user = (User) authentication.getPrincipal();
        model.put("citas", servicioMedico.obtenerCitasDelDia(user.getUsername()));

        return new ModelAndView("medico/citas-del-dia", model);
    }

    @RequestMapping("/agregar-especialidad")
    public ModelAndView irAAgregarEspecialidad(Authentication authentication) {
        ModelMap model = new ModelMap();
        User user = (User) authentication.getPrincipal();
        FormularioEspecialidades formEspecialidades = new FormularioEspecialidades();

        //formEspecialidades.setEspecialidadesTodas(servicioCita.allEspecialidades());
        formEspecialidades.setUsuario(servicioMedico.consultarMedicoPorEmail(user.getUsername()));
        model.put("especialidadesTodas", servicioCita.allEspecialidades());
        model.put("datos", formEspecialidades);

        return new ModelAndView("medico/agregar-especialidad", model);
    }

    @RequestMapping(path = "/agregar-especialidad", method = RequestMethod.POST)
    public ModelAndView irAAgregarEspecialidad(FormularioEspecialidades formulario, BindingResult result, Authentication authentication) {
        List<String> errores = new ArrayList<>();
        ModelMap model = new ModelMap();
        User user = (User) authentication.getPrincipal();
        //FormularioEspecialidades formEspecialidades = new FormularioEspecialidades();

        formulario.setEspecialidadesTodas(servicioCita.allEspecialidades());
        formulario.setUsuario(servicioMedico.consultarMedicoPorEmail(user.getUsername()));

// Si hubo errores del formulario, se cargan al modelo y se regresa a la vista sin procesar
        if (result.hasErrors() || formulario.getEspecialidadNueva() == 0 ){
            result.getFieldErrors().forEach(error -> {
                errores.add(error.getDefaultMessage());
            });

            if (formulario.getEspecialidadNueva() == 0)
                errores.add("Seleccione especialidad de la lista");

            model.put("errores", errores);
            model.put("especialidadesTodas", servicioCita.allEspecialidades());
            model.put("datos", formulario);
            return new ModelAndView("medico/agregar-especialidad", model);
        }
// Se agrega Especialidad
        try {
            servicioMedico.addEspecialidad(formulario.getUsuario(), formulario.getEspecialidadNueva());
        }
        catch (RuntimeException e) {
            errores.add(e.getMessage());
            model.put("errores", errores);
        }

        model.put("especialidadesTodas", servicioCita.allEspecialidades());
        model.put("datos", formulario);

// Se regresa a la vista con la información de Especialidades actualizada
        return new ModelAndView("medico/agregar-especialidad", model);
    }

}
