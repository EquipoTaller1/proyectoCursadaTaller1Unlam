package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroMedico;

public interface ServicioMedico {

    Persona consultarMedico(String matricula);

    void registrarMedico(FormularioRegistroMedico formulario, Persona persona);
}
