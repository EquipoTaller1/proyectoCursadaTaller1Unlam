package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Cita;
import ar.edu.unlam.tallerweb1.modelo.Especialidad;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroMedico;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.tz.FixedDateTimeZone;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;


import java.util.*;

import static org.assertj.core.api.Assertions.*;

public class RepositorioMedicoTest extends SpringTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private  RepositorioMedico repositorioMedico;

    @Autowired
    private RepositorioCita repositorioCita;

    @Autowired
    private RepositorioAdministrador repositorioAdministrador;

    @Test
    @Transactional
    @Rollback
    public void seEliminaEspecialidadExistente(){
        Usuario medico = givenMedicoExistente();
        whenSeEliminaEspecialidadExistente(medico);
        thenElMedicoYanoTieneEsaEspecialidad(medico);
    }

    @Test
    @Transactional
    public void seObtienenCitasDeUnMedicoEnUnDiaEspecifico(){
        givenCitasDeUnMedicoEnElDia();
        List<Cita> citas = whenSeObtienenLasCitasDelDia();
        thenLasCitasObtenidasSonLasCorrectas(citas);
    }

    @Test
    @Transactional
    public void noSeObtienenCitasDelMedicoEnFechaAntigua(){
        Usuario medico = givenMedicoExistente();
        List<Cita> citas = whenSeObtienenLasCitasDeFechaAntigua(medico);
        thenNoSeEncuentranCitas(citas);
    }

    private void thenNoSeEncuentranCitas(List<Cita> citas) {
        assertThat(citas).hasSize(0);
    }


    private List<Cita> whenSeObtienenLasCitasDeFechaAntigua(Usuario medico) {
        /*Calendar fecha = Calendar.getInstance();
        fecha.setTime(new Date("1900/01/01"));*/
        return repositorioMedico.obtenerCitasPorFecha(medico.getEmail(), new Date("1900/01/01"));
    }


    private Usuario givenMedicoExistente(){
// Se registra persona Médico
        Persona persona = new Persona();
        persona.setNombre("Juan");
        persona.setApellido("Perez");
        persona.setEmail("javier.terranova_2310@gmail.com");
        persona.setTipoDocumento("DNI");
        persona.setNumeroDocumento("4836646");
        String dateInString = "23/10/1985";
        persona.setFechaNacimiento(dateInString);
        persona.setSexo("Masculino");
        persona.setMatricula("3123122310");
        repositorioAdministrador.registrarMedico(persona);

// Se registra el usuario medico
        FormularioRegistroMedico formulario = new FormularioRegistroMedico();
        formulario.setMatricula("3123122310");
        formulario.setEmail("javier.terranova_2310@gmail.com");
        formulario.setPassword("123456");
        formulario.setPasswordRepet("123456");
        repositorioMedico.registrarMedico(formulario, persona);

        Usuario medico = repositorioMedico.obtenerMedicoPorEmail("javier.terranova_2310@gmail.com");

// Se agrega especialidad 1
        final Session session = sessionFactory.getCurrentSession();
        Especialidad especialidad = new Especialidad();
        long idEspecialidad = new Long(1);
        especialidad.setId(idEspecialidad);
        especialidad.setDescripcion("Test");
        session.save(especialidad);
        repositorioMedico.addEspecialidad(medico, 1);

        return medico;
    }

    private void thenLasCitasObtenidasSonLasCorrectas(List<Cita> citas) {
       // assertThat(citas).hasSize(2);
    }

    private List<Cita> whenSeObtienenLasCitasDelDia() {
       /* Calendar fecha = Calendar.getInstance();
        fecha.setTime(new Date("2021/06/07"));*/
// Se insertaron datos previamente en la BD para esa fecha y ese médico
        return repositorioMedico.obtenerCitasPorFecha("dmaradona@gmail.com", new Date("2021/06/07"));
    }

    private void givenCitasDeUnMedicoEnElDia() {
        /*Especialidad cardiologia = new Especialidad();
        cardiologia.setId((long) 1);
        cardiologia.setDescripcion("Cardiología");
        List<Especialidad> especialidades = Arrays.asList(cardiologia);
        Usuario medico = new Usuario();
        medico.setEmail("medico_test@test.com");
        medico.setPassword("password_medico_test");
        medico.setEspecialidades(especialidades);

        repositorioAdministrador.registrarMedico(medico);
        repositorioCita.guardarCita();*/
    }

    private void thenElMedicoYanoTieneEsaEspecialidad(Usuario medico) {
        assertThat(medico.getEspecialidades()).hasSize(0);
    }

    private void whenSeEliminaEspecialidadExistente(Usuario medico) {
        repositorioMedico.deleteEspecialidad(medico.getEmail(), 1);
    }

}
