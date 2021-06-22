package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.Excepciones.ErrorCita;
import ar.edu.unlam.tallerweb1.modelo.Cita;
import ar.edu.unlam.tallerweb1.modelo.formularios.DatosCita;
import ar.edu.unlam.tallerweb1.modelo.formularios.DatosCitaUrgencia;
import ar.edu.unlam.tallerweb1.servicios.ServicioCita;
import ar.edu.unlam.tallerweb1.servicios.ServicioPaciente;
import org.joda.time.LocalDateTime;
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

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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

    @RequestMapping("/citas/createUrgencia")
    public ModelAndView irACrearCitaUrgencia(){
        ModelMap model = new ModelMap();
        model.put("datos", new DatosCitaUrgencia());
        model.put("especialidades", servicioCita.allEspecialidades());

        return new ModelAndView("mis-citas/createUrgencia", model);
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

    @RequestMapping(value = "/citas/storeUrgencia", method = RequestMethod.POST)
    public ModelAndView createUrgencia(@Valid DatosCitaUrgencia datosCitaUrgencia, BindingResult result, Authentication authentication){
        ModelMap model = new ModelMap();
        List<String> errores = new ArrayList<>();
        User user = (User) authentication.getPrincipal();

        LocalDateTime fecha = new LocalDateTime();

        

        int anio = fecha.getYear();
        int mes = fecha.getMonthOfYear();
        int dia = fecha.getDayOfYear();
        int hora = fecha.getHourOfDay();
        int minuto = fecha.getMinuteOfHour();

        String anioFecha = String.valueOf(anio);
        String mesFecha = String.valueOf(mes);
        String diaFecha = String.valueOf(dia);
        String horaFecha = String.valueOf(hora);
        String minutoFecha = String.valueOf(minuto);

        datosCitaUrgencia.setPaciente(user.getUsername());
        datosCitaUrgencia.setTipoCita(2);


        //el medico deberia seleccionarse dinamicamente teniendo en cuenta su ubicacion y la ubicacion del paciente
        datosCitaUrgencia.setMedico(4);
        datosCitaUrgencia.setHora(horaFecha + ":" + minutoFecha);
        datosCitaUrgencia.setFecha(anioFecha + "-" + mesFecha + "-" + diaFecha);




        if (result.hasErrors()){
            result.getFieldErrors().forEach(error -> {
                errores.add(error.getDefaultMessage());
            });

            model.put("errores", errores);
            return new ModelAndView("redirect:/paciente/citas/createUrgencia", model);
        }

        try {
            servicioCita.create(datosCitaUrgencia);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/paciente/citas/index");
    }

    @RequestMapping(value = "/mapa_cita/{idCita}", method = RequestMethod.GET)
    public ModelAndView irAMapaCita(@PathVariable Long idCita, Authentication authentication){
        ModelMap model = new ModelMap();
        List<String> errores = new ArrayList<>();

        User user = (User) authentication.getPrincipal();
        try {
            Cita cita = servicioPaciente.getCita(user.getUsername(), idCita);
            model.put("cita", cita);
        }catch (ErrorCita e){
            errores.add(e.toString());
        }

        return new ModelAndView("/mis-citas/mapa-cita", model);
    }

}
