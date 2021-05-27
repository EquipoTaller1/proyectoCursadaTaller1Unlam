package ar.edu.unlam.tallerweb1.controladores;
import ar.edu.unlam.tallerweb1.Excepciones.AfiliadoNoExisteException;
import ar.edu.unlam.tallerweb1.Excepciones.AfiliadoRegistradoException;
import ar.edu.unlam.tallerweb1.Excepciones.EmailEnUsoException;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroPaciente;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPaciente;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioAdministrador;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioLogin;
import ar.edu.unlam.tallerweb1.servicios.ServicioLoginImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

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
        FormularioRegistroPaciente formularioRegistroPaciente = givenIngresoLosDatos();

        ModelAndView mav = whenCreoElPaciente(formularioRegistroPaciente);

        thenElPacienteSeCreaConExito(mav);
    }

    @Test (expected = AfiliadoNoExisteException.class)
    public void testQueUnPacienteNoAfiliadoNoSePuedaRegistrar(){
        FormularioRegistroPaciente formularioRegistroPaciente = givenIngresoLosDatos();

        whenElPacienteNoSeEncuentra(formularioRegistroPaciente);
    }

    @Test (expected = AfiliadoRegistradoException.class)
    public void testQueUnPacienteYaRegistradoNoSePuedaVolverARegistrar(){
        FormularioRegistroPaciente formularioRegistroPaciente = givenIngresoLosDatos();

        whenElPacienteYaEstaRegistrado(formularioRegistroPaciente);
    }

    @Test (expected = EmailEnUsoException.class)
    public void testQueElEmailYaEstaRegistrado(){
        FormularioRegistroPaciente formularioRegistroPaciente = givenIngresoLosDatos();

        whenElEmailYaEstaRegitrado(formularioRegistroPaciente);
    }

    private FormularioRegistroPaciente givenIngresoLosDatos() {
        FormularioRegistroPaciente formularioExito = new FormularioRegistroPaciente();
        formularioExito.setAfiliado("1111");
        formularioExito.setEmail("paciente-registro@paciente.com");
        formularioExito.setPassword("bla");
        formularioExito.setPasswordRepet("bla");

        return formularioExito;
    }

    private ModelAndView whenCreoElPaciente(FormularioRegistroPaciente formularioRegistroPaciente) {
        BeanPropertyBindingResult error = new BeanPropertyBindingResult(formularioRegistroPaciente, "formularioRegistroPaciente");
        Persona persona = new Persona();

        when(repositorioPaciente.consultarAfiliado(formularioRegistroPaciente.getAfiliado())).thenReturn(persona);
        ModelAndView mav = controladorRegistro.store(formularioRegistroPaciente, error);
        verify(repositorioUsuario).userByEmail(formularioRegistroPaciente.getEmail());
        verify(repositorioPaciente).consultarAfiliado(formularioRegistroPaciente.getAfiliado());
        verify(repositorioPaciente).registrarPaciente(formularioRegistroPaciente, persona);

        return mav;
    }

    private void whenElPacienteNoSeEncuentra(FormularioRegistroPaciente formularioRegistroPaciente) {
        List<FieldError> errores = new ArrayList<>();

        when(repositorioPaciente.consultarAfiliado(formularioRegistroPaciente.getAfiliado())).thenReturn(null);
        servicioLogin.registrarPaciente(formularioRegistroPaciente, errores);
        verify(repositorioUsuario).userByEmail(formularioRegistroPaciente.getEmail());
        verify(repositorioPaciente).consultarAfiliado(formularioRegistroPaciente.getAfiliado());
        verify(repositorioPaciente, never()).registrarPaciente(formularioRegistroPaciente, new Persona());

    }

    private void whenElPacienteYaEstaRegistrado(FormularioRegistroPaciente formularioRegistroPaciente) {
        List<FieldError> errores = new ArrayList<>();
        Persona persona = new Persona();
        persona.setUsuario(new Usuario());

        when(repositorioPaciente.consultarAfiliado(formularioRegistroPaciente.getAfiliado())).thenReturn(persona);
        servicioLogin.registrarPaciente(formularioRegistroPaciente, errores);
        verify(repositorioUsuario).userByEmail(formularioRegistroPaciente.getEmail());
        verify(repositorioPaciente).consultarAfiliado(formularioRegistroPaciente.getAfiliado());
        verify(repositorioPaciente, never()).registrarPaciente(formularioRegistroPaciente, persona);

    }

    private void whenElEmailYaEstaRegitrado(FormularioRegistroPaciente formularioRegistroPaciente) {
        List<FieldError> errores = new ArrayList<>();

        when(repositorioUsuario.userByEmail(formularioRegistroPaciente.getEmail())).thenReturn(new Usuario());
        servicioLogin.registrarPaciente(formularioRegistroPaciente, errores);
        verify(repositorioUsuario).userByEmail(formularioRegistroPaciente.getEmail());
        verify(repositorioPaciente, never()).consultarAfiliado(formularioRegistroPaciente.getAfiliado());
        verify(repositorioPaciente, never()).registrarPaciente(formularioRegistroPaciente, new Persona());

    }

    private void thenElPacienteSeCreaConExito(ModelAndView mav) {
        assertThat(mav.getViewName()).isEqualTo("redirect:/login?exito");
    }

}
