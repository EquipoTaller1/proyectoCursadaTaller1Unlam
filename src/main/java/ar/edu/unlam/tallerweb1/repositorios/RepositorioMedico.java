package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroMedico;

import java.util.List;

public interface RepositorioMedico {

    List<Usuario> obtenerTodos();

    Persona consultarMedico(String matricula);

    void registrarMedico(FormularioRegistroMedico formulario, Persona persona);
}

