package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.Excepciones.FaltanDatosParaElRegistroException;
import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioAdministrador;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
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

    private void whenLaQuieroRegistrar(Persona persona) {
        _servicioAdministrador.registrar(persona);
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
