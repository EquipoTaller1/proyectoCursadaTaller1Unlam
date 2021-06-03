package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.Excepciones.FaltanDatosParaElRegistroException;
import ar.edu.unlam.tallerweb1.Excepciones.PersonaYaExisteException;
import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.text.ParseException;


import static org.assertj.core.api.Assertions.*;


public class RepositorioAdministradorTest extends SpringTest {


    @Autowired
    private RepositorioAdministrador repositorioAdministrador;

    @Test
    @Transactional
    @Rollback
    public void sePuedeRegistrarUnaPersona() throws ParseException {

        Persona persona = givenUnaPersonaNoExistenteQueBrindaTodosSusDatos();

        whenLaQuieroRegistrar(persona);

        thenLaRegistroCorrectamente(persona);
    }

    @Test(expected = PersonaYaExisteException.class)
    @Transactional
    @Rollback
    public void noSePuedeRegistrarUnaPersonaExistente() throws PersonaYaExisteException, ParseException {

        Persona persona = givenUnaPersonaQueYaExiste();

        whenLaQuierovolverARegistrar(persona);
    }

    @Test(expected = FaltanDatosParaElRegistroException.class)
    @Transactional
    @Rollback
    public void errorAlRegistrarPersonaQueLeFaltanDatos() throws ParseException {

        Persona persona = givenUnaPersonaQueLefaltanDatos();

        whenLaQuieroRegistrar(persona);

    }

    private Persona givenUnaPersonaQueLefaltanDatos() {
        Persona persona = new Persona();

        persona.setNumeroAfiliado("");
        persona.setNombre("");
        persona.setApellido("");
        persona.setEmail("");
        persona.setTipoDocumento("");
        persona.setNumeroDocumento("");
        persona.setSexo("");

        return persona;
    }

    public Persona givenUnaPersonaQueYaExiste() throws ParseException {

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


        session().save(persona);
        return persona;
    }

    private Persona givenUnaPersonaNoExistenteQueBrindaTodosSusDatos() throws ParseException {
        Persona persona = new Persona();

        persona.setNumeroAfiliado("9999");
        persona.setNombre("Pepe");
        persona.setApellido("Argento");
        persona.setEmail("nherrera3276@gmail.com");
        persona.setTipoDocumento("DNI");
        persona.setNumeroDocumento("4836646");

        String dateInString = "23/10/1985";

        persona.setFechaNacimiento(dateInString);
        persona.setSexo("Masculino");

        return persona;

    }

    private void whenLaQuieroRegistrar(Persona persona) throws ParseException {

        repositorioAdministrador.registrar(persona);
    }

    public void whenLaQuierovolverARegistrar(Persona persona) throws ParseException {

        repositorioAdministrador.registrar(persona);
    }


    private void thenLaRegistroCorrectamente(Persona persona) {

        assertThat(persona).isNotNull();
    }


}
