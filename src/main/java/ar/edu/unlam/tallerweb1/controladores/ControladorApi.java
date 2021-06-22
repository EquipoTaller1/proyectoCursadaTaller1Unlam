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
    private ServicioPaciente servicioPaciente;

    @Autowired
    public  ControladorApi(ServicioMapa servicioMapa, ServicioCita servicioCita, ServicioPaciente servicioPaciente){
        this.servicioMapa = servicioMapa;
        this.servicioCita = servicioCita;
        this.servicioPaciente = servicioPaciente;
    }

    @RequestMapping("/medicos")
    public ResponseEntity<List<Usuario>> getMedicos(){

        return new ResponseEntity<>(servicioMapa.obtenerMedicosTodos(), HttpStatus.OK);
    }

    @RequestMapping("medicos/{idEspecialidad}")
    public ResponseEntity<List<Usuario>> getMedicosByEspecialidad(@PathVariable Long idEspecialidad){

        return new ResponseEntity<>(servicioCita.medicosByEspecialidad(idEspecialidad), HttpStatus.OK);
    }

    @RequestMapping("/cita/{idCita}")
    public ResponseEntity<List<Usuario>> getMedicos(@PathVariable Long idCita){

    //    try {
        List<Usuario> medicos = new ArrayList<>();
        Cita cita = servicioPaciente.getCita(idCita);

        medicos.add(cita.getMedico());

            return new ResponseEntity<>(medicos, HttpStatus.OK);
    /*    }catch (ErrorCita e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
*/
    }


}
