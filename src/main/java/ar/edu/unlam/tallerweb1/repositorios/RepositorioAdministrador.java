package ar.edu.unlam.tallerweb1.repositorios;
import ar.edu.unlam.tallerweb1.modelo.Persona;

import java.text.ParseException;

public interface RepositorioAdministrador {

    //Persona consultarAfiliado(String numeroAfiliado);

    void registrar(Persona persona) throws ParseException;
}
