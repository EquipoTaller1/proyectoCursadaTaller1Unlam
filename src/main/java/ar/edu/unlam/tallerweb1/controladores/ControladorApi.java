package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioMapa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ControladorApi {

    private ServicioMapa servicioMapa;

    @Autowired
    public  ControladorApi(ServicioMapa servicioMapa){ this.servicioMapa = servicioMapa;}

    @RequestMapping("/medicos")
    public ResponseEntity<List<Usuario>> getMedicos(){

        return new ResponseEntity<>(servicioMapa.obtenerMedicosTodos(), HttpStatus.OK);
    }
}
