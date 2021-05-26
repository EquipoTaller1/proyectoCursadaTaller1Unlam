package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface ServicioUsuario {

    Usuario consultarUsuario (Usuario usuario);

    Usuario userByEmail (String email);

    void createUser(Persona persona);

    Usuario consultarPorId(Long id);


}
