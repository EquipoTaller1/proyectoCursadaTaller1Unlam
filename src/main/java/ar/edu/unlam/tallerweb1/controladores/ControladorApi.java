package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.Excepciones.ErrorCita;
import ar.edu.unlam.tallerweb1.modelo.Cita;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioCita;
import ar.edu.unlam.tallerweb1.servicios.ServicioMapa;
import ar.edu.unlam.tallerweb1.servicios.ServicioPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ControladorApi {

    private ServicioMapa servicioMapa;
    private ServicioCita servicioCita;

    @Autowired
    public  ControladorApi(ServicioMapa servicioMapa, ServicioCita servicioCita){
        this.servicioMapa = servicioMapa;
        this.servicioCita = servicioCita;
    }

    @RequestMapping("/medicos")
    public ResponseEntity<List<Usuario>> getMedicos(){

        return new ResponseEntity<>(servicioMapa.obtenerMedicosTodos(), HttpStatus.OK);
    }

    @RequestMapping("medicos/{idEspecialidad}")
    public ResponseEntity<List<Usuario>> getMedicosByEspecialidad(@PathVariable Long idEspecialidad){

        return new ResponseEntity<>(servicioCita.medicosByEspecialidad(idEspecialidad), HttpStatus.OK);
    }


}
