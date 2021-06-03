package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.Excepciones.FaltanDatosParaElRegistroException;
import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuario;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.mockito.Mockito.*;

public class ServicioUsuarioTest extends SpringTest {

    private ServicioUsuario _servicioUsuario;
    private RepositorioUsuario _repositorioUsuarioMock;

    @Before
    public void init() {
        _repositorioUsuarioMock = mock(RepositorioUsuario.class);
        _servicioUsuario = new ServicioUsuarioImpl(_repositorioUsuarioMock);
    }



    @Test(expected = FaltanDatosParaElRegistroException.class)
    @Transactional
    @Rollback
    public void errorAlCrearUsuario() {

        Persona persona = givenUnUsuarioNoRegistrado();

        whenLoquieroRegistrar(persona);
    }

    private Persona givenUnUsuarioNoRegistrado() {

        Usuario usuario = new Usuario();
        usuario.setEmail("nico@gmail.com");

        Persona persona = new Persona();
        persona.setNumeroAfiliado("9999");
        persona.setUsuario(usuario);

        return persona;

    }

    private void whenLoquieroRegistrar(Persona persona) {

        _servicioUsuario.createUser(persona);
    }


}
