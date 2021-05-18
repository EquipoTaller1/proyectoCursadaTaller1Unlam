package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface RepositorioPersona {
    Persona consultarAfiliado(String numeroAfiliado);
}
