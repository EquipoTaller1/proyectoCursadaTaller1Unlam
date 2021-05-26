package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.Excepciones.PersonaYaExisteException;
import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.assertj.core.api.Assertions.*;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositorioUsuarioTest extends SpringTest {

    @Autowired
    RepositorioUsuario _repositorioUsuario;


    @Test
    @Transactional
    @Rollback
    public void sePuedeRegistrarUsuario() {

        Usuario usuario = givenUnUsuarioInexistente();

        Usuario usuarioBuscado = whenLoQuieroRegistrar(usuario);

        thenLaPuedoRegistrarCorrectamente(usuarioBuscado);


    }

    @Test
    @Transactional
    @Rollback
    public void sePuedeConsultarUnUsuarioExistente() {

        Usuario usuario = givenUnUsuarioExistente();

        Usuario usuarioConsultado = whenLoQuieroConsultar(usuario);

        thenLaConsultaSeRealizaCorrectamente(usuarioConsultado);


    }

    @Test
    @Transactional
    @Rollback
    public void sePuedeBuscarUsuarioPorEmail() {

        Usuario usuario = givenUnUsuarioExistente();

        Usuario usuarioConsultadoPorEmail = whenLoQuieroConsultarPorEmail(usuario.getEmail());

        thenLaConsultaSeRealizaCorrectamente(usuarioConsultadoPorEmail);


    }

    @Test
    @Transactional
    @Rollback
    public void sePuedeBuscarUsuarioPorId() {

        Usuario usuario = givenUnUsuarioExistente();

        Usuario usuarioConsultadoPorId = whenLoQuieroConsultarPorId(usuario.getId());

        thenLaConsultaSeRealizaCorrectamente(usuarioConsultadoPorId);


    }

    private Usuario whenLoQuieroConsultarPorId(Long id) {
        return _repositorioUsuario.consultarPorId(id);
    }

    private Usuario whenLoQuieroConsultarPorEmail(String email) {

        return _repositorioUsuario.userByEmail(email);
    }

    private void thenLaConsultaSeRealizaCorrectamente(Usuario usuarioConsultado) {
        assertThat(usuarioConsultado).isNotNull();
    }

    private Usuario whenLoQuieroConsultar(Usuario usuario) {

        return _repositorioUsuario.consultarUsuario(usuario);
    }


    private Usuario givenUnUsuarioExistente() {

        Usuario usuario = new Usuario();


        usuario.setEmail("nico@gmail.com");
        usuario.setPassword("123");

        _repositorioUsuario.createUser(usuario);

        return usuario;

    }

    private void thenLaPuedoRegistrarCorrectamente(Usuario usuario) {

        assertThat(usuario).isNotNull();

    }

    private Usuario whenLoQuieroRegistrar(Usuario usuario) {

        _repositorioUsuario.createUser(usuario);

        return _repositorioUsuario.consultarUsuario(usuario);


    }

    private Usuario givenUnUsuarioInexistente() {

        Usuario usuario = new Usuario();

        usuario.setEmail("nico@gmail.com");
        usuario.setPassword("123");

        return usuario;

    }


}
