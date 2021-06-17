package ar.edu.unlam.tallerweb1.controladores;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioMedico;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPaciente;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioAdministrador;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioRegistroUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioRegistroUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioRegistroUsuarioImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControladorRegistroUsuarioTest {

    private ControladorRegistroUsuario controladorRegistroUsuario;
    private ServicioRegistroUsuario servicioRegistroUsuario;
    private RepositorioPaciente repositorioPaciente;
    private RepositorioRegistroUsuario repositorioRegistroUsuario;
    private RepositorioAdministrador repositorioAdministrador;
    private RepositorioMedico repositorioMedico;

    @Before
    public void init(){
        repositorioRegistroUsuario = mock(RepositorioRegistroUsuario.class);
        repositorioAdministrador = mock(RepositorioAdministrador.class);
        repositorioPaciente = mock(RepositorioPaciente.class);
        repositorioMedico = mock(RepositorioMedico.class);

        servicioRegistroUsuario = new ServicioRegistroUsuarioImpl(repositorioRegistroUsuario, repositorioAdministrador, repositorioPaciente, repositorioMedico);
        controladorRegistroUsuario = new ControladorRegistroUsuario(servicioRegistroUsuario);
    }

    @Test
    public void testIrARegistro(){
        ModelAndView mav = controladorRegistroUsuario.irARegistro("");

        assertThat(mav.getViewName()).isEqualTo("auth/register");
        assertThat(mav.getModel().get("formularioPaciente")).isNotNull();
    }

    @Test
    public void testIrARegistroDeMedico(){
        ModelAndView mav = controladorRegistroUsuario.irARegistroMedico("");
        assertThat(mav.getViewName()).isEqualTo("auth/registerMedico");
        assertThat(mav.getModel().get("formularioMedico")).isNotNull();
    }

}
