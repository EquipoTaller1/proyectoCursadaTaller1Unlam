package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioCita;
import ar.edu.unlam.tallerweb1.servicios.ServicioMapa;
import ar.edu.unlam.tallerweb1.servicios.ServicioMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ControladorApi {

    private ServicioMapa servicioMapa;
    private ServicioCita servicioCita;
    private ServicioMedico servicioMedico;

    @Autowired
    public  ControladorApi(ServicioMapa servicioMapa, ServicioCita servicioCita, ServicioMedico servicioMedico){
        this.servicioMapa = servicioMapa;
        this.servicioCita = servicioCita;
        this.servicioMedico = servicioMedico;
    }

    @RequestMapping("/medicos")
    public ResponseEntity<List<Usuario>> getMedicos(){

        return new ResponseEntity<>(servicioMapa.obtenerMedicosTodos(), HttpStatus.OK);
    }

    @RequestMapping("medicos/{idEspecialidad}")
    public ResponseEntity<List<Usuario>> getMedicosByEspecialidad(@PathVariable Long idEspecialidad){

        return new ResponseEntity<>(servicioCita.medicosByEspecialidad(idEspecialidad), HttpStatus.OK);
    }

    @RequestMapping("horarios/{medico}/{fecha}")
    public List<String> horariosMedico(@PathVariable Long medico, @PathVariable String fecha){

        return this.servicioMedico.getHorariosDia(medico, fecha);
    }

}
