package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.Excepciones.ErrorEnFormatoDeFechaException;
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



    @Test()
    @Transactional
    @Rollback
    public void sePuedeRegistrarPersonaMedicoCorrectamente() throws ParseException {

        Persona persona = givenUnaPersonaMedicoConDatosCorrectos();

        whenLaQuieroRegistrarMedico(persona);

        verify(_repositorioAdministrador, times(1)).registrarMedico(persona);
    }

    private void whenLaQuieroRegistrarMedico(Persona persona) throws ParseException {
        _servicioAdministrador.registrarMedico(persona);
    }

    private Persona givenUnaPersonaMedicoConDatosCorrectos() {
        Persona persona = new Persona();


        persona.setNombre("Pepe");
        persona.setApellido("Argento");
        persona.setEmail("nherrera3276@gmail.com");
        persona.setTipoDocumento("DNI");
        persona.setNumeroDocumento("4836646");
        String dateInString = "23/10/1985";
        persona.setFechaNacimiento(dateInString);
        persona.setSexo("masculino");
        persona.setMatricula("31231231");

        return persona;
    }

    @Test(expected = FaltanDatosParaElRegistroException.class)
    @Transactional
    @Rollback
    public void errorAlRegistrarPersonaConDatosIncorrectos() throws ParseException {

        Persona persona = givenUnaPersonaConDatosInCorrectos();

        whenLaQuieroRegistrar(persona);
    }

    @Test
    @Transactional
    @Rollback
    public void sePuedeEnviarEmailCorrectamente() throws ParseException {

        FormularioPersona formulario = givenUnFormularioDeRegistroCorrecto();

        whenQuieroEnviarEmailDeRegistroExitoso(formulario);

        thenSeEnviaCorrectamente(formulario);
    }

    @Test
    @Transactional
    @Rollback
    public void fallaElEnvioEmailPorDatosIncorrectosEnElFormulario() throws ParseException {

        FormularioPersona formulario = givenUnFormularioDeRegistroIncorrecto();

        whenQuieroEnviarEmailDeRegistroExitoso(formulario);

        thenElEnvioFalla(formulario);
    }

    @Test(expected = ErrorEnFormatoDeFechaException.class)
    @Transactional
    @Rollback
    public void siLaFechaDeNacimientoEsIncorrectaElRegistroFalla() throws ParseException {

        String dateInString = "29/02/2021";

        whenChequeoLafecha(dateInString);
    }


    private FormularioPersona givenUnFormularioDeRegistroCorrecto() throws ParseException {

        FormularioPersona formulario = new FormularioPersona();
        formulario.setNumeroAfiliado("90909090");
        formulario.setNombre("Luis");
        formulario.setApellido("Suarez");
        formulario.setEmail("tallerUnoPruebas@gmail.com");
        formulario.setTipoDocumento("DNI");
        formulario.setNumeroDocumento("123123123");
        String dateInString = "23/10/1985";
        formulario.setFechaNacimiento(dateInString);
        formulario.setSexo("Masculino");

        return formulario;
    }

    private Persona givenUnaPersonaConDatosCorrectos() throws ParseException {
        Persona persona = new Persona();

        persona.setNumeroAfiliado("9999");
        persona.setNombre("Pepe");
        persona.setApellido("Argento");
        persona.setEmail("nherrera3276@gmail.com");
        persona.setTipoDocumento("DNI");
        persona.setNumeroDocumento("4836646");
        String dateInString = "23/10/1985";
        persona.setFechaNacimiento(dateInString);
        persona.setSexo("masculino");

        return persona;
    }


    private Persona givenUnaPersonaConDatosInCorrectos() throws ParseException {

        Persona persona = new Persona();

        persona.setNumeroAfiliado("321321");
        persona.setNombre("nicolas");
        persona.setApellido("Herrera");
        persona.setEmail("nherrera3276@gmail.com");
        persona.setTipoDocumento("DNI");
        persona.setNumeroDocumento("31231231");
        String dateInString = "";
        persona.setFechaNacimiento(dateInString);
        persona.setSexo("otre");

        return persona;
    }


    private FormularioPersona givenUnFormularioDeRegistroIncorrecto() throws ParseException {
        FormularioPersona formulario = new FormularioPersona();
        formulario.setNombre("Luis");
        formulario.setApellido("Suarez");
        formulario.setTipoDocumento("DNI");
        formulario.setNumeroDocumento("123123123");
        String dateInString = "23/10/1985";
        formulario.setFechaNacimiento(dateInString);

        return formulario;
    }

    private void whenQuieroEnviarEmailDeRegistroExitoso(FormularioPersona formulario) {

        _servicioAdministrador.enviarEmailDeRegistro(formulario);
    }


    private void whenLaQuieroRegistrar(Persona persona) throws ParseException {

        _servicioAdministrador.registrar(persona);
    }

    private void whenChequeoLafecha(String fecha) {
        _servicioAdministrador.chequearFecha(fecha);
    }


    private void thenElEnvioFalla(FormularioPersona formulario) {

        assertThat(_servicioAdministrador.enviarEmailDeRegistro(formulario)).isFalse();
    }

    private void thenSeEnviaCorrectamente(FormularioPersona formulario) {

        assertThat(_servicioAdministrador.enviarEmailDeRegistro(formulario)).isTrue();
    }


}
