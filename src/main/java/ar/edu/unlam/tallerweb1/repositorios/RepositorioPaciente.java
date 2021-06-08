package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.formularios.DatosRegistroPaciente;

public interface RepositorioPaciente {

    void registrarPaciente(DatosRegistroPaciente datosRegistroPaciente, Persona persona);

    Persona consultarAfiliado(String numeroAfiliado);
}
