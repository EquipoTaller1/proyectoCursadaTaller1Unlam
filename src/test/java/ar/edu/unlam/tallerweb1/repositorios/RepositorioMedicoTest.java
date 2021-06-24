package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroMedico;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.tz.FixedDateTimeZone;
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
    private  RepositorioMedico repositorioMedico;

    @Autowired
    private RepositorioCita repositorioCita;

    @Autowired
    private RepositorioAdministrador repositorioAdministrador;

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
        String medico = givenMedicoExistente();
        List<Cita> citas = whenSeObtienenLasCitasDeFechaAntigua(medico);
        thenNoSeEncuentranCitas(citas);
    }

    @Test
    @Transactional
    public void testQueSePuedaGuardarUnaAgenda() throws ParseException {
        FormularioRegistroMedico datosMedico = givenCreoUnMedico();
        Usuario medico = whenCreoUnaAgenda(datosMedico);
        thenLaAgendaSeGuardaConExito(medico);
    }

    @Test
    @Transactional
    public void testQueSePuedaActualizarUnDiaEnLaAgenda() throws ParseException {
        FormularioRegistroMedico datosMedico = givenCreoUnMedico();
        Usuario medico = whenCreoUnaAgenda(datosMedico);
        Agenda agenda = whenActualizoLaAgenda(medico);
        thenLaAgendaSeActualizoConExito(medico, agenda);
    }

    private void thenLaAgendaSeActualizoConExito(Usuario medico, Agenda agenda) {
        List<Agenda> agendas = this.repositorioMedico.obtenerAgenda(medico.getEmail());
        assertThat(agendas.get(0).getHoraHasta().toString()).isEqualTo(agenda.getHoraHasta().toString());
    }

    private Agenda whenActualizoLaAgenda(Usuario medico) {
        Agenda agenda = this.repositorioMedico.obtenerAgenda(medico.getEmail()).get(0);

        LocalTime horaHasta = LocalTime.parse("12:00:00");
        agenda.setHoraHasta(horaHasta);
        this.repositorioMedico.actualizarAgenda(agenda);

        return agenda;
    }

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

    private void thenLaAgendaSeGuardaConExito(Usuario medico) {
        List<Agenda> agendas = this.repositorioMedico.obtenerAgenda(medico.getEmail());

        assertThat(agendas).isNotEmpty();
    }


    private void thenNoSeEncuentranCitas(List<Cita> citas) {
        //assertThat(citas).hasSize(0);
    }


    private List<Cita> whenSeObtienenLasCitasDeFechaAntigua(String medico) {
        /*Calendar fecha = Calendar.getInstance();
        fecha.setTime(new Date("1900/01/01"));*/
        return repositorioMedico.obtenerCitasPorFecha(medico, LocalDate.parse("1900/01/01"));
    }

    private String givenMedicoExistente() {
        return "dmaradona@gmail.com";
    }


    private void thenLasCitasObtenidasSonLasCorrectas(List<Cita> citas) {
       // assertThat(citas).hasSize(2);
    }

    private List<Cita> whenSeObtienenLasCitasDelDia() {
       /* Calendar fecha = Calendar.getInstance();
        fecha.setTime(new Date("2021/06/07"));*/
// Se insertaron datos previamente en la BD para esa fecha y ese médico
        return repositorioMedico.obtenerCitasPorFecha("dmaradona@gmail.com", LocalDate.parse("2021/06/07"));
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

}
