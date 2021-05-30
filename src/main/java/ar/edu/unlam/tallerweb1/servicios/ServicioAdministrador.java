package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioPersona;

public interface ServicioAdministrador {

    void registrar(Persona persona);

    boolean enviarEmailDeRegistro(FormularioPersona formulario);
}
