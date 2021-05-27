package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.Excepciones.FaltanDatosParaElRegistroException;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;

public class ServicioUsuarioImpl implements ServicioUsuario {

    private RepositorioUsuario _repositorioUsuario;

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario _repositorioUsuario)
    {
        this._repositorioUsuario = _repositorioUsuario;
    }

    @Override
    public void createUser(Persona persona) {

        if (persona.getNumeroAfiliado() == null || persona.getUsuario().getEmail() == null || persona.getUsuario().getPassword() == null){
            throw new FaltanDatosParaElRegistroException();
        }

        _repositorioUsuario.createUser(persona.getUsuario());

    }


    @Override
    public Usuario consultarUsuario(Usuario usuario) {

        return null;
    }

    @Override
    public Usuario userByEmail(String email) {
        return null;
    }


    @Override
    public Usuario consultarPorId(Long id) {
        return null;
    }
}
