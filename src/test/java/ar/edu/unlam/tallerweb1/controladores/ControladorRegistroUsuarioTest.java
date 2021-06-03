package ar.edu.unlam.tallerweb1.controladores;
import ar.edu.unlam.tallerweb1.Excepciones.*;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroMedico;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroPaciente;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioMedico;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPaciente;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioAdministrador;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioRegistroUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioRegistroUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioRegistroUsuarioImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

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



    @Test
    public void testQueUnPacienteSePuedaRegistrar(){
        FormularioRegistroPaciente formularioRegistroPaciente = givenIngresoLosDatos();

        ModelMap model = whenCreoElPaciente(formularioRegistroPaciente);

        thenElPacienteSeCreaConExito(model);
    }

    @Test
    public void testQueUnMedicoSePuedaRegistrar() throws MedicoNoExisteException {
        FormularioRegistroMedico formularioRegistroPaciente = givenIngresoLosDatosDelMedico();

        ModelMap model = whenCreoElMedico(formularioRegistroPaciente);

        thenElMedicoSeCreaConExito(model);
    }

    private ModelMap whenCreoElMedico(FormularioRegistroMedico formularioRegistroMedico) throws MedicoNoExisteException {
        List<FieldError> errores = new ArrayList<>();
        Persona persona = new Persona();

        when(repositorioMedico.consultarMedico(formularioRegistroMedico.getMatricula())).thenReturn(persona);
        ModelMap model = servicioRegistroUsuario.registrarMedico(formularioRegistroMedico, errores);
        verify(repositorioRegistroUsuario).userByEmail(formularioRegistroMedico.getEmail());
        verify(repositorioMedico).consultarMedico(formularioRegistroMedico.getMatricula());
        verify(repositorioMedico).registrarMedico(formularioRegistroMedico, persona);

        return model;
    }

    private void thenElMedicoSeCreaConExito(ModelMap model) {
        assertThat(model.get("exito")).isEqualTo("El usuario se creo correctamente");
    }

    private FormularioRegistroMedico givenIngresoLosDatosDelMedico() {
        FormularioRegistroMedico formularioExito = new FormularioRegistroMedico();
        formularioExito.setMatricula("1111");
        formularioExito.setEmail("medico-registro@medicomedicomedico.com");
        formularioExito.setPassword("bla");
        formularioExito.setPasswordRepet("bla");

        return formularioExito;
    }

    @Test (expected = AfiliadoNoExisteException.class)
    public void testQueUnPacienteNoAfiliadoNoSePuedaRegistrar(){
        FormularioRegistroPaciente formularioRegistroPaciente = givenIngresoLosDatos();

        whenElPacienteNoSeEncuentra(formularioRegistroPaciente);
    }

    @Test (expected = MedicoNoExisteException.class)
    public void testQueUnMedicoNoRegistradoNoSePuedaRegistrar() throws MedicoNoExisteException {
        FormularioRegistroMedico formularioRegistroMedico = givenIngresoLosDatosDelMedico();

        whenElMedicoNoSeEncuentra(formularioRegistroMedico);
    }

    private void whenElMedicoNoSeEncuentra(FormularioRegistroMedico formularioRegistroMedico) throws MedicoNoExisteException {
        List<FieldError> errores = new ArrayList<>();

        when(repositorioMedico.consultarMedico(formularioRegistroMedico.getMatricula())).thenReturn(null);
        ModelMap model = servicioRegistroUsuario.registrarMedico(formularioRegistroMedico, errores);
        verify(repositorioRegistroUsuario).userByEmail(formularioRegistroMedico.getEmail());
        verify(repositorioMedico).consultarMedico(formularioRegistroMedico.getMatricula());
        verify(repositorioMedico, never()).registrarMedico(formularioRegistroMedico, new Persona());

    }

    @Test (expected = AfiliadoRegistradoException.class)
    public void testQueUnPacienteYaRegistradoNoSePuedaVolverARegistrar(){
        FormularioRegistroPaciente formularioRegistroPaciente = givenIngresoLosDatos();

        whenElPacienteYaEstaRegistrado(formularioRegistroPaciente);
    }

    @Test (expected = MedicoYaRegistradoException.class)
    public void testQueUnMedicoYaRegistradoNoSePuedaVolverARegistrar() throws MedicoNoExisteException {

        FormularioRegistroMedico formularioRegistroMedico = givenIngresoLosDatosDelMedico();

        whenElMedicoYaEstaRegistrado(formularioRegistroMedico);
    }

    private void whenElMedicoYaEstaRegistrado(FormularioRegistroMedico formularioRegistroMedico) throws MedicoNoExisteException {
        List<FieldError> errores = new ArrayList<>();
        Persona persona = new Persona();
        persona.setUsuario(new Usuario());

        when(repositorioMedico.consultarMedico(formularioRegistroMedico.getMatricula())).thenReturn(persona);
        ModelMap model = servicioRegistroUsuario.registrarMedico(formularioRegistroMedico, errores);
        verify(repositorioRegistroUsuario).userByEmail(formularioRegistroMedico.getEmail());
        verify(repositorioMedico).consultarMedico(formularioRegistroMedico.getMatricula());
        verify(repositorioMedico, never()).registrarMedico(formularioRegistroMedico, persona);

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

    private ModelMap whenCreoElPaciente(FormularioRegistroPaciente formularioRegistroPaciente) {
        List<FieldError> errores = new ArrayList<>();
        Persona persona = new Persona();

        when(repositorioPaciente.consultarAfiliado(formularioRegistroPaciente.getAfiliado())).thenReturn(persona);
        ModelMap model = servicioRegistroUsuario.registrarPaciente(formularioRegistroPaciente, errores);
        verify(repositorioRegistroUsuario).userByEmail(formularioRegistroPaciente.getEmail());
        verify(repositorioPaciente).consultarAfiliado(formularioRegistroPaciente.getAfiliado());
        verify(repositorioPaciente).registrarPaciente(formularioRegistroPaciente, persona);

        return model;
    }

    private void whenElPacienteNoSeEncuentra(FormularioRegistroPaciente formularioRegistroPaciente) {
        List<FieldError> errores = new ArrayList<>();

        when(repositorioPaciente.consultarAfiliado(formularioRegistroPaciente.getAfiliado())).thenReturn(null);
        ModelMap model = servicioRegistroUsuario.registrarPaciente(formularioRegistroPaciente, errores);
        verify(repositorioRegistroUsuario).userByEmail(formularioRegistroPaciente.getEmail());
        verify(repositorioPaciente).consultarAfiliado(formularioRegistroPaciente.getAfiliado());
        verify(repositorioPaciente, never()).registrarPaciente(formularioRegistroPaciente, new Persona());

    }

    private void whenElPacienteYaEstaRegistrado(FormularioRegistroPaciente formularioRegistroPaciente) {
        List<FieldError> errores = new ArrayList<>();
        Persona persona = new Persona();
        persona.setUsuario(new Usuario());

        when(repositorioPaciente.consultarAfiliado(formularioRegistroPaciente.getAfiliado())).thenReturn(persona);
        ModelMap model = servicioRegistroUsuario.registrarPaciente(formularioRegistroPaciente, errores);
        verify(repositorioRegistroUsuario).userByEmail(formularioRegistroPaciente.getEmail());
        verify(repositorioPaciente).consultarAfiliado(formularioRegistroPaciente.getAfiliado());
        verify(repositorioPaciente, never()).registrarPaciente(formularioRegistroPaciente, persona);

    }

    private void whenElEmailYaEstaRegitrado(FormularioRegistroPaciente formularioRegistroPaciente) {
        List<FieldError> errores = new ArrayList<>();

        when(repositorioRegistroUsuario.userByEmail(formularioRegistroPaciente.getEmail())).thenReturn(new Usuario());
        ModelMap model = servicioRegistroUsuario.registrarPaciente(formularioRegistroPaciente, errores);
        verify(repositorioRegistroUsuario).userByEmail(formularioRegistroPaciente.getEmail());
        verify(repositorioPaciente, never()).consultarAfiliado(formularioRegistroPaciente.getAfiliado());
        verify(repositorioPaciente, never()).registrarPaciente(formularioRegistroPaciente, new Persona());

    }

    private void thenElPacienteSeCreaConExito(ModelMap model) {
        assertThat(model.get("exito")).isEqualTo("El usuario se creo correctamente");
    }

}
