package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioPersona;

import java.text.ParseException;
import java.util.Date;

public interface ServicioAdministrador {

    void registrar(Persona persona) throws ParseException;

    boolean enviarEmailDeRegistro(FormularioPersona formulario);

    void chequearFecha(String fecha);

    void registrarMedico(Persona persona) throws ParseException;
}
