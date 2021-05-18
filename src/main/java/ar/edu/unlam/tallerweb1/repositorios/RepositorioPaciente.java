package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroPaciente;

public interface RepositorioPaciente {

    void registrarPaciente(FormularioRegistroPaciente formularioRegistroPaciente, Persona persona);
}
