package ar.edu.unlam.tallerweb1.controladores;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.formularios.DatosRegistroPaciente;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPaciente;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioAdministrador;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioLogin;
import ar.edu.unlam.tallerweb1.servicios.ServicioLoginImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControladorRegistroTest {

    private ControladorRegistro controladorRegistro;
    private ServicioLogin servicioLogin;
    private RepositorioPaciente repositorioPaciente;
    private RepositorioUsuario repositorioUsuario;
    private RepositorioAdministrador repositorioAdministrador;

    @Before
    public void init(){
        repositorioUsuario = mock(RepositorioUsuario.class);
        repositorioAdministrador = mock(RepositorioAdministrador.class);
        repositorioPaciente = mock(RepositorioPaciente.class);

        servicioLogin = new ServicioLoginImpl(repositorioUsuario, repositorioAdministrador, repositorioPaciente);
        controladorRegistro = new ControladorRegistro(servicioLogin);
    }

    @Test
    public void testIrARegistro(){
        ModelAndView mav = controladorRegistro.irARegistro("");

        assertThat(mav.getViewName()).isEqualTo("auth/register");
        assertThat(mav.getModel().get("formularioPaciente")).isNotNull();
    }

    @Test
    public void testQueUnPacienteSePuedaRegistrar(){
        DatosRegistroPaciente datosRegistroPaciente = givenIngresoLosDatos();

        ModelAndView mav = whenCreoElPaciente(datosRegistroPaciente);

        thenElPacienteSeCreaConExito(mav);
    }


    private DatosRegistroPaciente givenIngresoLosDatos() {
        DatosRegistroPaciente formularioExito = new DatosRegistroPaciente();
        formularioExito.setAfiliado("1111");
        formularioExito.setEmail("paciente-registro@paciente.com");
        formularioExito.setPassword("bla");
        formularioExito.setPasswordRepet("bla");

        return formularioExito;
    }

    private ModelAndView whenCreoElPaciente(DatosRegistroPaciente datosRegistroPaciente) {
        BeanPropertyBindingResult error = new BeanPropertyBindingResult(datosRegistroPaciente, "datosRegistroPaciente");
        Persona persona = new Persona();

        when(repositorioPaciente.consultarAfiliado(datosRegistroPaciente.getAfiliado())).thenReturn(persona);
        ModelAndView mav = controladorRegistro.store(datosRegistroPaciente, error);
        verify(repositorioUsuario).userByEmail(datosRegistroPaciente.getEmail());
        verify(repositorioPaciente).consultarAfiliado(datosRegistroPaciente.getAfiliado());
        verify(repositorioPaciente).registrarPaciente(datosRegistroPaciente, persona);

        return mav;
    }

    private void thenElPacienteSeCreaConExito(ModelAndView mav) {
        assertThat(mav.getViewName()).isEqualTo("redirect:/login?exito");
    }

}
