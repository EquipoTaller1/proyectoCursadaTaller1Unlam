package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.Excepciones.FaltanDatosParaElRegistroException;
import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioPersona;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioAdministrador;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.*;

public class ServicioAdministradorTest extends SpringTest {

    private ServicioAdministrador _servicioAdministrador;
    private RepositorioAdministrador _repositorioAdministrador;

    @Before
    public void init() {
        _repositorioAdministrador = mock(RepositorioAdministrador.class);
        _servicioAdministrador = new ServicioAdministradorImpl(_repositorioAdministrador);
    }

    @Test()
    @Transactional
    @Rollback
    public void sePuedeRegistrarPersonaCorrectamente() throws ParseException {

        Persona persona = givenUnaPersonaConDatosCorrectos();

        whenLaQuieroRegistrar(persona);

        verify(_repositorioAdministrador, times(1)).registrar(persona);

    }

    @Test(expected = FaltanDatosParaElRegistroException.class)
    @Transactional
    @Rollback
    public void errorAlRegistrarPersona() {

        Persona persona = givenUnaPersonaConDatosInCorrectos();

        whenLaQuieroRegistrar(persona);
    }

    //se comentan estos test ya que travis falla al trabajar con smtp y queda la IC en rojo.
    /*@Test
    @Transactional
    @Rollback
    public void sePuedeEnviarEmailCorrectamente() {

        FormularioPersona formulario = givenUnFormularioDeRegistroCorrecto();

        whenQuieroEnviarEmailDeRegistroExitoso(formulario);

        thenSeEnviaCorrectamente(formulario);

    }

    @Test
    @Transactional
    @Rollback
    public void fallaElEnvioEmailPorDatosIncorrectosEnElFormulario() {

        FormularioPersona formulario = givenUnFormularioDeRegistroIncorrecto();

        whenQuieroEnviarEmailDeRegistroExitoso(formulario);

        thenElEnvioFalla(formulario);

    }*/

    private FormularioPersona givenUnFormularioDeRegistroCorrecto() {

        FormularioPersona formulario = new FormularioPersona();
        formulario.setNombre("Luis");
        formulario.setApellido("Suarez");
        formulario.setTipoDocumento("DNI");
        formulario.setNumeroDocumento("123123123");
        formulario.setEmail("tallerUnoPruebas@gmail.com");
        formulario.setFechaNacimiento(new java.sql.Date(10, 10, 2021));
        formulario.setSexo("Masculino");
        formulario.setNumeroAfiliado("90909090");

        return formulario;
    }

    private Persona givenUnaPersonaConDatosCorrectos() throws ParseException {
        Persona persona = new Persona();

        persona.setNombre("Pepe");
        persona.setApellido("Argento");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString = "23/10/1985";
        Date date = sdf.parse(dateInString);
        persona.setFechaNacimiento(date);

        persona.setNumeroAfiliado("9999");
        persona.setNumeroDocumento("4836646");
        persona.setTipoDocumento("DNI");

        return persona;
    }


    private Persona givenUnaPersonaConDatosInCorrectos() {

        Persona persona = new Persona();

        return persona;
    }


    private FormularioPersona givenUnFormularioDeRegistroIncorrecto() {
        FormularioPersona formulario = new FormularioPersona();
        formulario.setNombre("Luis");
        formulario.setApellido("Suarez");
        formulario.setTipoDocumento("DNI");
        formulario.setNumeroDocumento("123123123");

        return formulario;
    }

    private void whenQuieroEnviarEmailDeRegistroExitoso(FormularioPersona formulario) {
        _servicioAdministrador.enviarEmailDeRegistro(formulario);
    }


    private void whenLaQuieroRegistrar(Persona persona) {

        _servicioAdministrador.registrar(persona);
    }


    private void thenElEnvioFalla(FormularioPersona formulario) {
        assertThat(_servicioAdministrador.enviarEmailDeRegistro(formulario)).isFalse();
    }

    private void thenSeEnviaCorrectamente(FormularioPersona formulario) {
        assertThat(_servicioAdministrador.enviarEmailDeRegistro(formulario)).isTrue();
    }


}
