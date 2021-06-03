package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioMedico;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPaciente;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioAdministrador;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioRegistroUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioRegistroUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioRegistroUsuarioImpl;
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
    private ServicioRegistroUsuario servicioRegistroUsuario;
    private ServicioUserDetaillsService servicioUserDetaillsService;
    private RepositorioRegistroUsuario repositorioRegistroUsuario;
    private RepositorioAdministrador repositorioPersona;
    private RepositorioPaciente repositorioPaciente;
    private RepositorioMedico repositorioMedico;

    @Before
    public void init(){
        repositorioRegistroUsuario = mock(RepositorioRegistroUsuario.class);
        repositorioPersona = mock(RepositorioAdministrador.class);
        repositorioPaciente = mock(RepositorioPaciente.class);
        repositorioMedico= mock(RepositorioMedico.class);

        servicioRegistroUsuario = new ServicioRegistroUsuarioImpl(repositorioRegistroUsuario, repositorioPersona, repositorioPaciente, repositorioMedico);
        servicioUserDetaillsService = new ServicioUserDetaillsService(servicioRegistroUsuario);
        controladorLogin = new ControladorLogin(servicioRegistroUsuario);
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

    @Test
    public void testQueUnUsuarioMedicoSePuedaLoguear(){

        Usuario usuario = givenMedicoRegistrado();

        UserDetails userDetails = whenElMedicoSeLoguea(usuario);

        thenElUsuarioSeLogueaConExito(userDetails, usuario);
    }

    private UserDetails whenElMedicoSeLoguea(Usuario usuario) {
        when(repositorioRegistroUsuario.userByEmail(usuario.getEmail())).thenReturn(usuario);
        UserDetails userDetails = servicioUserDetaillsService.loadUserByUsername(usuario.getEmail());
        verify(repositorioRegistroUsuario).userByEmail(usuario.getEmail());

        return userDetails;
    }

    private Usuario givenMedicoRegistrado() {

        Persona persona = new Persona();
        persona.setNombre("saraza");

        Usuario usuario = new Usuario();
        usuario.setEmail("medico@medicomedicomedico.com");
        usuario.setPassword("bla");
        usuario.setRol("Medico");
        usuario.setPersona(persona);

        return  usuario;
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
        when(repositorioRegistroUsuario.userByEmail(usuario.getEmail())).thenReturn(usuario);
        UserDetails userDetails = servicioUserDetaillsService.loadUserByUsername(usuario.getEmail());
        verify(repositorioRegistroUsuario).userByEmail(usuario.getEmail());

        return userDetails;
    }

    private void whenElUsuarioSeLogueaException(Usuario usuario) {
        when(repositorioRegistroUsuario.userByEmail(usuario.getEmail())).thenReturn(null);
        UserDetails userDetails = servicioUserDetaillsService.loadUserByUsername(usuario.getEmail());
        verify(repositorioRegistroUsuario).userByEmail(usuario.getEmail());
    }

    private void thenElUsuarioSeLogueaConExito(UserDetails userDetails, Usuario usuario) {
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(usuario.getEmail());
    }

}
