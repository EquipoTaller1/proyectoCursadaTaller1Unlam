package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.Excepciones.AfiliadoNoExisteException;
import ar.edu.unlam.tallerweb1.Excepciones.AfiliadoRegistradoException;
import ar.edu.unlam.tallerweb1.Excepciones.EmailEnUsoException;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.DatosRegistroPaciente;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioAdministrador;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPaciente;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuario;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class ServicioLoginTest {

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
    }

    @Test(expected = AfiliadoNoExisteException.class)
    public void testQueUnPacienteNoAfiliadoNoSePuedaRegistrar(){
        DatosRegistroPaciente datosRegistroPaciente = givenIngresoLosDatos();

        whenElPacienteNoSeEncuentra(datosRegistroPaciente);
    }

    @Test (expected = AfiliadoRegistradoException.class)
    public void testQueUnPacienteYaRegistradoNoSePuedaVolverARegistrar(){
        DatosRegistroPaciente datosRegistroPaciente = givenIngresoLosDatos();

        whenElPacienteYaEstaRegistrado(datosRegistroPaciente);
    }

    @Test (expected = EmailEnUsoException.class)
    public void testQueElEmailYaEstaRegistrado(){
        DatosRegistroPaciente datosRegistroPaciente = givenIngresoLosDatos();

        whenElEmailYaEstaRegitrado(datosRegistroPaciente);
    }

    private DatosRegistroPaciente givenIngresoLosDatos() {
        DatosRegistroPaciente formularioExito = new DatosRegistroPaciente();
        formularioExito.setAfiliado("1111");
        formularioExito.setEmail("paciente-registro@paciente.com");
        formularioExito.setPassword("bla");
        formularioExito.setPasswordRepet("bla");

        return formularioExito;
    }

    private void whenElPacienteNoSeEncuentra(DatosRegistroPaciente datosRegistroPaciente) {
        when(repositorioPaciente.consultarAfiliado(datosRegistroPaciente.getAfiliado())).thenReturn(null);
        servicioLogin.registrarPaciente(datosRegistroPaciente);
        verify(repositorioUsuario).userByEmail(datosRegistroPaciente.getEmail());
        verify(repositorioPaciente).consultarAfiliado(datosRegistroPaciente.getAfiliado());
        verify(repositorioPaciente, never()).registrarPaciente(datosRegistroPaciente, new Persona());
        // TODO separar controlador y servicios
    }

    private void whenElPacienteYaEstaRegistrado(DatosRegistroPaciente datosRegistroPaciente) {
        Persona persona = new Persona();
        persona.setUsuario(new Usuario());

        when(repositorioPaciente.consultarAfiliado(datosRegistroPaciente.getAfiliado())).thenReturn(persona);
        servicioLogin.registrarPaciente(datosRegistroPaciente);
        verify(repositorioUsuario).userByEmail(datosRegistroPaciente.getEmail());
        verify(repositorioPaciente).consultarAfiliado(datosRegistroPaciente.getAfiliado());
        verify(repositorioPaciente, never()).registrarPaciente(datosRegistroPaciente, persona);

    }

    private void whenElEmailYaEstaRegitrado(DatosRegistroPaciente datosRegistroPaciente) {
        when(repositorioUsuario.userByEmail(datosRegistroPaciente.getEmail())).thenReturn(new Usuario());
        servicioLogin.registrarPaciente(datosRegistroPaciente);
        verify(repositorioUsuario).userByEmail(datosRegistroPaciente.getEmail());
        verify(repositorioPaciente, never()).consultarAfiliado(datosRegistroPaciente.getAfiliado());
        verify(repositorioPaciente, never()).registrarPaciente(datosRegistroPaciente, new Persona());

    }
}
