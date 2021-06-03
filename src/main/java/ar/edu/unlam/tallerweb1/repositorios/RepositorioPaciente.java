package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Cita;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.formularios.DatosRegistroPaciente;

import java.util.List;

public interface RepositorioPaciente {

    void registrarPaciente(DatosRegistroPaciente datosRegistroPaciente, Persona persona);

    Persona consultarAfiliado(String numeroAfiliado);

    List<Cita> obtenerCitas(String email);

    List<Cita> obtenerCitasProximas(String email);
}
