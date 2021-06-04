package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.Excepciones.*;
import ar.edu.unlam.tallerweb1.modelo.formularios.DatosRegistroPaciente;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroMedico;
import ar.edu.unlam.tallerweb1.servicios.ServicioRegistroUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorRegistroUsuario {

    private ServicioRegistroUsuario servicioRegistroUsuario;

    @Autowired
    public ControladorRegistroUsuario(ServicioRegistroUsuario servicioRegistroUsuario){
        this.servicioRegistroUsuario = servicioRegistroUsuario;
    }


    // REGISTRO DE PACIENTES ---------------------------------------------------------

    @RequestMapping("/registro")
    public ModelAndView irARegistro(@RequestParam(value = "message", required = false) String message){
        ModelMap model = new ModelMap();
        if (message != null){
            model.put("message", message);
        }

        model.put("formularioPaciente", new DatosRegistroPaciente());

        return new ModelAndView("auth/register", model);
    }

    @RequestMapping("/registro/store")
    public ModelAndView store(@Valid DatosRegistroPaciente formulario, BindingResult result){
        ModelMap model = new ModelMap();
        model.put("formularioPaciente", formulario);
        List<String> errores = new ArrayList<>();

        if (result.hasErrors()){
            result.getFieldErrors().forEach(error -> {
                errores.add(error.getDefaultMessage());
            });

            model.put("errores", errores);
            return new ModelAndView("auth/register", model);
        }

        try {
            servicioRegistroUsuario.registrarPaciente(formulario);

            return new ModelAndView("redirect:/login?exito");
        }
        catch (RuntimeException e){
            errores.add(e.getMessage());
            model.put("errores", errores);

            return new ModelAndView("auth/register", model);
        }
    }


    // REGISTRO DE MEDICOS ---------------------------------------------------------


    @RequestMapping("/registro-medico")
    public ModelAndView irARegistroMedico(@RequestParam(value = "message", required = false) String message){
        ModelMap model = new ModelMap();
        if (message != null){
            model.put("message", message);
        }

        model.put("formularioMedico", new FormularioRegistroMedico());

        return new ModelAndView("auth/registerMedico", model);
    }


    @RequestMapping("/registro/storeMedico")
    public ModelAndView store(@Valid FormularioRegistroMedico formulario, BindingResult result){
        ModelMap model = new ModelMap();
        model.put("formularioMedico", formulario);
        List<String> errores = new ArrayList<>();

        if (result.hasErrors()){
            result.getFieldErrors().forEach(error -> {errores.add(error.getDefaultMessage());});
            model.put("errores", errores);
            return new ModelAndView("auth/registerMedico", model);
        };

        try {
            model = servicioRegistroUsuario.registrarMedico(formulario, result.getFieldErrors());

            return new ModelAndView("redirect:/login?exito", model);
        }
        catch (RuntimeException e){
            errores.add(e.getMessage());
            model.put("errores", errores);

            return new ModelAndView("auth/registerMedico", model);
        }



    }

}
