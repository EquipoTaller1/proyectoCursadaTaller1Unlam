package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Cita;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroMedico;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface RepositorioMedico {

    List<Usuario> obtenerTodos();

    Persona consultarMedico(String matricula);

    void registrarMedico(FormularioRegistroMedico formulario, Persona persona);

    List<Cita> obtenerCitasPorFecha(String email, Date fecha);
}

