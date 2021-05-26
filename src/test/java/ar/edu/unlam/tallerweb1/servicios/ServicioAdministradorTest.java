package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.Excepciones.FaltanDatosParaElRegistroException;
import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioAdministrador;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;
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
    public void sePuedeRegistrarPersonaCorrectamente() {

        Persona persona = givenUnaPersonaConDatosCorrectos();

        whenLaQuieroRegistrar(persona);

        verify(_repositorioAdministrador, times(1)).registrar(persona);

    }

    private void whenLaQuieroRegistrar(Persona persona) {
        _servicioAdministrador.registrar(persona);
    }

    private Persona givenUnaPersonaConDatosCorrectos() {
        Persona persona = new Persona();


        persona.setNombre("Pepe");
        persona.setApellido("Argento");
        persona.setFechaNacimiento(new Date(10, 10, 2012));
        persona.setNumeroAfiliado("9999");
        persona.setNumeroDocumento("4836646");
        persona.setTipoDocumento("DNI");

        return persona;
    }

    @Test(expected = FaltanDatosParaElRegistroException.class)
    @Transactional
    @Rollback
    public void errorAlRegistrarPersona() {

        Persona persona = givenUnaPersonaConDatosInCorrectos();

        whenLaQuieroRegistrar(persona);

    }

    private Persona givenUnaPersonaConDatosInCorrectos() {
        Persona persona = new Persona();

        return persona;
    }


}
