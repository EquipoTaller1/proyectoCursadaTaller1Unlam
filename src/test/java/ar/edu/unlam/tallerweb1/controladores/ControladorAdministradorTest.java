package ar.edu.unlam.tallerweb1.controladores;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroPaciente;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPaciente;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPersona;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioLogin;
import ar.edu.unlam.tallerweb1.servicios.ServicioLoginImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControladorAdministradorTest {

    private ControladorAdministrador controladorAdministrador;

    @Before
    public void init(){
        controladorAdministrador = new ControladorAdministrador();
    }

    @Test
    public void testIrARegistrarPersona(){
        ModelAndView mav = controladorAdministrador.registrarPersona();

        assertThat(mav.getViewName()).isEqualTo("administrador/registrar-persona");
        assertThat(mav.getModel().get("persona")).isNotNull();
    }

/*    @Test
    public void testQueUnPacienteSePuedaRegistrar(){
        FormularioRegistroPaciente formularioRegistroPaciente = givenIngresoLosDatos();

        ModelMap model = whenCreoElPaciente(formularioRegistroPaciente);

        thenElPacienteSeCreaConExito(model);
    }

    @Test
    public void testQueUnPacienteNoAfiliadoNoSePuedaRegistrar(){
        FormularioRegistroPaciente formularioRegistroPaciente = givenIngresoLosDatos();

        ModelMap model = whenElPacienteNoSeEncuentra(formularioRegistroPaciente);

        thenElRegistroFallaPorNoEstarAfiliado(model);
    }

    @Test
    public void testQueUnPacienteYaRegistradoNoSePuedaVolverARegistrar(){
        FormularioRegistroPaciente formularioRegistroPaciente = givenIngresoLosDatos();

        ModelMap model = whenElPacienteYaEstaRegistrado(formularioRegistroPaciente);

        thenElRegistroFallaPorTenerUsuario(model);
    }

    @Test
    public void testQueElEmailYaEstaRegistrado(){
        FormularioRegistroPaciente formularioRegistroPaciente = givenIngresoLosDatos();

        ModelMap model = whenElEmailYaEstaRegitrado(formularioRegistroPaciente);

        thenElRegistroFallaPorEmailRepetido(model);
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

        when(repositorioPersona.consultarAfiliado(formularioRegistroPaciente.getAfiliado())).thenReturn(persona);
        ModelMap model = servicioLogin.registrarPaciente(formularioRegistroPaciente, errores);
        verify(repositorioUsuario).userByEmail(formularioRegistroPaciente.getEmail());
        verify(repositorioPersona).consultarAfiliado(formularioRegistroPaciente.getAfiliado());
        verify(repositorioPaciente).registrarPaciente(formularioRegistroPaciente, persona);

        return model;
    }

    private ModelMap whenElPacienteNoSeEncuentra(FormularioRegistroPaciente formularioRegistroPaciente) {
        List<FieldError> errores = new ArrayList<>();

        when(repositorioPersona.consultarAfiliado(formularioRegistroPaciente.getAfiliado())).thenReturn(null);
        ModelMap model = servicioLogin.registrarPaciente(formularioRegistroPaciente, errores);
        verify(repositorioUsuario).userByEmail(formularioRegistroPaciente.getEmail());
        verify(repositorioPersona).consultarAfiliado(formularioRegistroPaciente.getAfiliado());
        verify(repositorioPaciente, never()).registrarPaciente(formularioRegistroPaciente, new Persona());

        return model;
    }

    private ModelMap whenElPacienteYaEstaRegistrado(FormularioRegistroPaciente formularioRegistroPaciente) {
        List<FieldError> errores = new ArrayList<>();
        Persona persona = new Persona();
        persona.setUsuario(new Usuario());

        when(repositorioPersona.consultarAfiliado(formularioRegistroPaciente.getAfiliado())).thenReturn(persona);
        ModelMap model = servicioLogin.registrarPaciente(formularioRegistroPaciente, errores);
        verify(repositorioUsuario).userByEmail(formularioRegistroPaciente.getEmail());
        verify(repositorioPersona).consultarAfiliado(formularioRegistroPaciente.getAfiliado());
        verify(repositorioPaciente, never()).registrarPaciente(formularioRegistroPaciente, persona);

        return model;
    }

    private ModelMap whenElEmailYaEstaRegitrado(FormularioRegistroPaciente formularioRegistroPaciente) {
        List<FieldError> errores = new ArrayList<>();

        when(repositorioUsuario.userByEmail(formularioRegistroPaciente.getEmail())).thenReturn(new Usuario());
        ModelMap model = servicioLogin.registrarPaciente(formularioRegistroPaciente, errores);
        verify(repositorioUsuario).userByEmail(formularioRegistroPaciente.getEmail());
        verify(repositorioPersona, never()).consultarAfiliado(formularioRegistroPaciente.getAfiliado());
        verify(repositorioPaciente, never()).registrarPaciente(formularioRegistroPaciente, new Persona());

        return model;
    }

    private void thenElPacienteSeCreaConExito(ModelMap model) {
        assertThat(model.get("exito")).isEqualTo("El usuario se creo correctamente");
    }

    private void thenElRegistroFallaPorNoEstarAfiliado(ModelMap model) {
        assertThat(model.get("errores")).isNotNull();
        ArrayList<String> errores = (ArrayList<String>)model.get("errores");
        assertThat(errores.contains("El numero de afiliado no es correcto")).isTrue();
    }

    private void thenElRegistroFallaPorTenerUsuario(ModelMap model) {
        assertThat(model.get("errores")).isNotNull();
        ArrayList<String> errores = (ArrayList<String>)model.get("errores");
        assertThat(errores.contains("El numero de afiliado ya se encuentra registrado")).isTrue();
    }

    private void thenElRegistroFallaPorEmailRepetido(ModelMap model) {
        assertThat(model.get("errores")).isNotNull();
        ArrayList<String> errores = (ArrayList<String>)model.get("errores");
        assertThat(errores.contains("El email ya se encuentra registrado")).isTrue();
    }

 */
}
