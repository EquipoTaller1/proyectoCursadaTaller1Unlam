package ar.edu.unlam.tallerweb1.repositorios;
import ar.edu.unlam.tallerweb1.modelo.Persona;

public interface RepositorioAdministrador {

    Persona consultarAfiliado(String numeroAfiliado);

    void registrar(Persona persona);
}
