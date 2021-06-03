package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroMedico;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioAdministrador;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioMedico;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPaciente;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioRegistroUsuario;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.mockito.Mockito.*;

public class ServicioRegistroUsuarioTest extends SpringTest {

    private ServicioRegistroUsuario servicioRegistroUsuario;
    private RepositorioRegistroUsuario repositorioRegistroUsuarioMock;
    private RepositorioAdministrador repositorioAdministrador;
    private RepositorioPaciente repositorioPaciente;
    private RepositorioMedico repositorioMedico;

    @Before
    public void init() {
        repositorioRegistroUsuarioMock = mock(RepositorioRegistroUsuario.class);
        repositorioAdministrador = mock(RepositorioAdministrador.class);
        repositorioPaciente = mock(RepositorioPaciente.class);
        repositorioMedico = mock(RepositorioMedico.class);
        servicioRegistroUsuario = new ServicioRegistroUsuarioImpl(repositorioRegistroUsuarioMock,repositorioAdministrador, repositorioPaciente,repositorioMedico);
    }



    @Test()
    @Transactional
    @Rollback
    public void sePuedeConsultarUsuario() {

        Persona persona = givenUnUsuarioRegistrado();

        when(repositorioRegistroUsuarioMock.consultarUsuario(persona.getUsuario())).thenReturn(persona.getUsuario());

        whenLoquieroConsultar(persona);
    }


    private void whenLoquieroConsultar(Persona persona) {
        repositorioRegistroUsuarioMock.consultarUsuario(persona.getUsuario());
    }

    private Persona givenUnUsuarioRegistrado() {

        return new Persona();

    }


}
