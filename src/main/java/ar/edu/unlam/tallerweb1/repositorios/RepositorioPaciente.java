package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Cita;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroPaciente;

import java.util.List;

public interface RepositorioPaciente {

    void registrarPaciente(FormularioRegistroPaciente formularioRegistroPaciente, Persona persona);

    List<Cita> obtenerCitas(String email);

    List<Cita> obtenerCitasProximas(String email);
}
