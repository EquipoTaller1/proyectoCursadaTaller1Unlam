package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Cita;
import ar.edu.unlam.tallerweb1.modelo.Especialidad;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroMedico;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ar.edu.unlam.tallerweb1.modelo.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;


import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
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
    @Rollback
    public void seObtienenCitasDeUnMedicoEnUnDiaEspecifico(){
        givenCitasDeUnMedicoEnElDia();
        List<Cita> citas = whenSeObtienenLasCitasDelDia();
        thenLasCitasObtenidasSonLasCorrectas(citas);
    }

    @Test
    @Transactional
    @Rollback
    public void noSeObtienenCitasDelMedicoEnFechaAntigua(){
        Usuario medico = givenMedicoExistente();
        List<Cita> citas = whenSeObtienenLasCitasDeFechaAntigua(medico);
        thenNoSeEncuentranCitas(citas);
    }

    @Test
    @Transactional
    @Rollback
    public void testQueSePuedaGuardarUnaAgenda() throws ParseException {
        FormularioRegistroMedico datosMedico = givenCreoUnMedico();
        Usuario medico = whenCreoUnaAgenda(datosMedico);
        thenLaAgendaSeGuardaConExito(medico);
    }

   /* @Test
    @Transactional
    @Rollback
    public void testQueSePuedaActualizarUnDiaEnLaAgenda() throws ParseException {
        FormularioRegistroMedico datosMedico = givenCreoUnMedico();
        Agenda agenda = whenActualizoLaAgenda(datosMedico.getEmail());
        thenLaAgendaSeActualizoConExito(datosMedico.getEmail(), agenda);
    }*/

    private FormularioRegistroMedico givenCreoUnMedico() throws ParseException {
        Persona persona = new Persona();
        persona.setNombre("enzo");
        persona.setEmail("medico@medico.com");
        persona.setApellido("jimenez");
        persona.setFechaNacimiento("18/04/1997");
        persona.setMatricula("1234");
        persona.setSexo("Masculino");
        persona.setNumeroDocumento("40258515");
        persona.setTipoDocumento("Dni");
        persona.setNumeroAfiliado("1234567");
        this.repositorioAdministrador.registrar(persona);

        FormularioRegistroMedico formularioRegistroMedico = new FormularioRegistroMedico();
        formularioRegistroMedico.setEmail("medico@medico.com");
        formularioRegistroMedico.setMatricula("1234");
        formularioRegistroMedico.setPassword("bla");
        formularioRegistroMedico.setPasswordRepet("bla");

        this.repositorioMedico.registrarMedico(formularioRegistroMedico, persona);

        return formularioRegistroMedico;
    }

    private void givenCitasDeUnMedicoEnElDia() {
        /*Especialidad cardiologia = new Especialidad();
        cardiologia.setId((long) 1);
        cardiologia.setDescripcion("Cardiolog??a");
        List<Especialidad> especialidades = Arrays.asList(cardiologia);
        Usuario medico = new Usuario();
        medico.setEmail("medico_test@test.com");
        medico.setPassword("password_medico_test");
        medico.setEspecialidades(especialidades);

        repositorioAdministrador.registrarMedico(medico);
        repositorioCita.guardarCita();*/
    }

    private Usuario givenMedicoExistente(){
// Se registra persona M??dico
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

    private Usuario whenCreoUnaAgenda(FormularioRegistroMedico datosMedico) {
        LocalTime horaDesde = LocalTime.now();
        LocalTime horaHasta = horaDesde.plusHours(8);

        Usuario medico = this.repositorioMedico.obtenerMedicoPorEmail(datosMedico.getEmail());

        Agenda agenda = new Agenda();
        agenda.setActivo(true);
        agenda.setDia("lunes");
        agenda.setMedico(medico);
        agenda.setHoraDesde(horaDesde);
        agenda.setHoraHasta(horaHasta);

        this.repositorioMedico.guardarAgenda(agenda);

        return medico;
    }

    private Agenda whenActualizoLaAgenda(String email) {
        Agenda agenda = this.repositorioMedico.obtenerAgenda(email).get(0);

        LocalTime horaHasta = LocalTime.parse("12:00");
        agenda.setHoraHasta(horaHasta);
        this.repositorioMedico.actualizarAgenda(agenda);

        return agenda;
    }


    private List<Cita> whenSeObtienenLasCitasDeFechaAntigua(Usuario medico) {
        /*Calendar fecha = Calendar.getInstance();
        fecha.setTime(new Date("1900/01/01"));*/

        return repositorioMedico.obtenerCitasPorFecha(medico.getEmail(), LocalDate.parse("1900-01-01"));
    }


    private List<Cita> whenSeObtienenLasCitasDelDia() {
       /* Calendar fecha = Calendar.getInstance();
        fecha.setTime(new Date("2021/06/07"));*/
// Se insertaron datos previamente en la BD para esa fecha y ese m??dico
        return repositorioMedico.obtenerCitasPorFecha("dmaradona@gmail.com", LocalDate.parse("2021-06-07"));
    }

    private void thenLasCitasObtenidasSonLasCorrectas(List<Cita> citas) {
        // assertThat(citas).hasSize(2);
    }

    private void thenLaAgendaSeGuardaConExito(Usuario medico) {
        List<Agenda> agendas = this.repositorioMedico.obtenerAgenda(medico.getEmail());

        assertThat(agendas).isNotEmpty();
    }

    private void thenNoSeEncuentranCitas(List<Cita> citas) {
        assertThat(citas).hasSize(0);
    }

    private void thenElMedicoYanoTieneEsaEspecialidad(Usuario medico) {
        assertThat(medico.getEspecialidades()).hasSize(0);
    }

    private void whenSeEliminaEspecialidadExistente(Usuario medico) {
        repositorioMedico.deleteEspecialidad(medico.getEmail(), 1);
    }

    private void thenLaAgendaSeActualizoConExito(String email, Agenda agenda) {
        List<Agenda> agendas = this.repositorioMedico.obtenerAgenda(email);
        assertThat(agendas.get(0).getHoraHasta().toString()).isEqualTo(agenda.getHoraHasta().toString());
    }
}
