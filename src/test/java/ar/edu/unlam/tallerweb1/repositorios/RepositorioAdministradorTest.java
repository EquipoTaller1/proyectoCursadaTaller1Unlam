package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.Excepciones.AfiliadoNoExisteException;
import ar.edu.unlam.tallerweb1.Excepciones.PersonaYaExisteException;
import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;


public class RepositorioAdministradorTest extends SpringTest {


    @Autowired
    private RepositorioAdministrador repositorioAdministrador;

    @Test
    @Transactional
    @Rollback
    public void sePuedeRegistrarUnaPersona() {

        Persona persona = givenUnaPersonaNoExistente();

        whenLaQuieroRegistrar(persona);

        thenLaRegistroCorrectamente(persona);
    }

    @Test(expected = PersonaYaExisteException.class)
    @Transactional
    @Rollback
    public void noSePuedeRegistrarUnaPersonaExistente() throws PersonaYaExisteException {

        Persona persona = givenUnaPersonaQueYaExiste();

        whenLaQuierovolverARegistrar(persona);
    }

    @Test
    @Transactional
    @Rollback
    public void sePuedeConsultarUnAfiliadoExistente() {

        Persona persona = givenUnaPersonaQueYaExiste();

        Persona personaBuscada = whenLaQuieroConsultar(persona);

        thenLaEncuentroCorrectamente(personaBuscada);
    }

    @Test(expected = AfiliadoNoExisteException.class)
    @Transactional
    @Rollback
    public void errorAlConsultarAfiliadoInexistente() {

        Persona persona = givenUnaPersonaNoExistente();

        Persona personaBuscada = whenLaQuieroConsultar(persona);
    }

    public Persona givenUnaPersonaQueYaExiste() {

        Persona persona = new Persona();
        persona.setNumeroAfiliado("11111111111111");
        session().save(persona);
        return persona;
    }


    private Persona givenUnaPersonaNoExistente() {

        Persona persona = new Persona();

        persona.setNumeroAfiliado("9997");
        return persona;
    }

    private void whenLaQuieroRegistrar(Persona persona) {

        repositorioAdministrador.registrar(persona);
    }

    private Persona whenLaQuieroConsultar(Persona persona) {

        return repositorioAdministrador.consultarAfiliado(persona.getNumeroAfiliado());
    }

    public void whenLaQuierovolverARegistrar(Persona persona) {

        repositorioAdministrador.registrar(persona);
    }


    private void thenLaEncuentroCorrectamente(Persona persona) {
        assertThat(persona).isNotNull();
    }

    private void thenLaRegistroCorrectamente(Persona persona) {

        assertThat(persona).isNotNull();
    }


}
