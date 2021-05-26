package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPaciente;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioAdministrador;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioLogin;
import ar.edu.unlam.tallerweb1.servicios.ServicioLoginImpl;
import ar.edu.unlam.tallerweb1.servicios.ServicioUserDetaillsService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.servlet.ModelAndView;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ControladorLoginTest {
    private ControladorLogin controladorLogin;
    private ServicioLogin servicioLogin;
    private ServicioUserDetaillsService servicioUserDetaillsService;
    private RepositorioUsuario repositorioUsuario;
    private RepositorioAdministrador repositorioPersona;
    private RepositorioPaciente repositorioPaciente;

    @Before
    public void init(){
        repositorioUsuario = mock(RepositorioUsuario.class);
        repositorioPersona = mock(RepositorioAdministrador.class);
        repositorioPaciente = mock(RepositorioPaciente.class);

        servicioLogin = new ServicioLoginImpl(repositorioUsuario, repositorioPersona, repositorioPaciente);
        servicioUserDetaillsService = new ServicioUserDetaillsService(servicioLogin);
        controladorLogin = new ControladorLogin(servicioLogin);
    }

    @Test
    public void testIrALogin(){
        ModelAndView mav = controladorLogin.loginPage("", "", "");

        assertThat(mav.getViewName()).isEqualTo("auth/login");
        assertThat(mav.getModel()).isNotNull();
    }

    @Test
    public void testQueUnUsuarioSePuedaLoguear(){
        Usuario usuario = givenUsuarioRegistrado();

        UserDetails userDetails = whenElUsuarioSeLoguea(usuario);

        thenElUsuarioSeLogueaConExito(userDetails, usuario);
    }

    @Test (expected = UsernameNotFoundException.class)
    public void testQueUnUsuarioNoRegistradoNoIngrese(){
        Usuario usuario = givenUsuarioNoRegistrado();

        whenElUsuarioSeLogueaException(usuario);
    }

    private Usuario givenUsuarioRegistrado() {
        Persona persona = new Persona();
        persona.setNombre("saraza");

        Usuario usuario = new Usuario();
        usuario.setEmail("paciente@paciente.com");
        usuario.setPassword("bla");
        usuario.setRol("Paciente");
        usuario.setPersona(persona);

        return  usuario;
    }

    private Usuario givenUsuarioNoRegistrado() {
        Usuario usuario = new Usuario();
        usuario.setEmail("noregistrado@paciente.com");

        return usuario;
    }

    private UserDetails whenElUsuarioSeLoguea(Usuario usuario) {
        when(repositorioUsuario.userByEmail(usuario.getEmail())).thenReturn(usuario);
        UserDetails userDetails = servicioUserDetaillsService.loadUserByUsername(usuario.getEmail());
        verify(repositorioUsuario).userByEmail(usuario.getEmail());

        return userDetails;
    }

    private void whenElUsuarioSeLogueaException(Usuario usuario) {
        when(repositorioUsuario.userByEmail(usuario.getEmail())).thenReturn(null);
        UserDetails userDetails = servicioUserDetaillsService.loadUserByUsername(usuario.getEmail());
        verify(repositorioUsuario).userByEmail(usuario.getEmail());
    }

    private void thenElUsuarioSeLogueaConExito(UserDetails userDetails, Usuario usuario) {
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(usuario.getEmail());
    }

}
