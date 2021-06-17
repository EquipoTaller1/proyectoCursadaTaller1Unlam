package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.Excepciones.ErrorEnFormatoDeFechaException;
import ar.edu.unlam.tallerweb1.Excepciones.FaltanDatosParaElRegistroException;

import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioPersona;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioAdministrador;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;


import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.*;

public class ServicioAdministradorTest {

    private ServicioAdministrador _servicioAdministrador;

    private RepositorioAdministrador _repositorioAdministrador;


    @Before
    public void init() {
        _repositorioAdministrador = mock(RepositorioAdministrador.class);

        _servicioAdministrador = new ServicioAdministradorImpl(_repositorioAdministrador);
    }

    @Test()
    public void sePuedeRegistrarPersonaCorrectamente() throws ParseException {

        Persona persona = givenUnaPersonaConDatosCorrectos();

        whenLaQuieroRegistrar(persona);

        verify(_repositorioAdministrador, times(1)).registrar(persona);

        thenElEmailSeEnviaCorrectamente(persona);


    }

    private void thenElEmailSeEnviaCorrectamente(Persona persona) {
        assertThat(_servicioAdministrador.enviarEmailDeRegistro(persona.toFormularioPersona())).isTrue();
    }


    @Test()
    public void sePuedeRegistrarPersonaMedicoCorrectamente() throws ParseException {

        Persona persona = givenUnaPersonaMedicoConDatosCorrectos();

        whenLaQuieroRegistrarMedico(persona);

        verify(_repositorioAdministrador, times(1)).registrarMedico(persona);
    }

    @Test(expected = FaltanDatosParaElRegistroException.class)
    public void errorAlRegistrarPersonaConDatosIncorrectos() throws ParseException {

        Persona persona = givenUnaPersonaConDatosInCorrectos();

        whenLaQuieroRegistrar(persona);
    }


    @Test
    public void fallaElEnvioEmailPorDatosIncorrectosEnElFormulario() throws ParseException {

        FormularioPersona formulario = givenUnFormularioDeRegistroIncorrecto();

        whenQuieroEnviarEmailDeRegistroExitoso(formulario);

        thenElEnvioFalla(formulario);
    }

    @Test(expected = ErrorEnFormatoDeFechaException.class)
    public void siLaFechaDeNacimientoEsIncorrectaElRegistroFalla() throws ParseException {

        String dateInString = "29/02/2021";

        whenChequeoLafecha(dateInString);
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

    private void whenLaQuieroRegistrarMedico(Persona persona) throws ParseException {
        _servicioAdministrador.registrarMedico(persona);
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


}
