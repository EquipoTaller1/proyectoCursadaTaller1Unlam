package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.Excepciones.EspecialidadRepetida;
import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Agenda;
import ar.edu.unlam.tallerweb1.modelo.Cita;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

import ar.edu.unlam.tallerweb1.repositorios.RepositorioAdministrador;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioMedico;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPaciente;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioRegistroUsuario;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class ServicioMedicoTest {

    private ServicioAdministrador _servicioAdministrador;
    private RepositorioAdministrador _repositorioAdministrador;
    private ServicioRegistroUsuario _servicioRegistroUsuario;
    private RepositorioRegistroUsuario _repositorioRegistroUsuario;
    private ServicioMedico _servicioMedico;
    private RepositorioMedico _repositorioMedico;

    private RepositorioPaciente _repositorioPaciente;

    @Before
    public void init() {
        _repositorioAdministrador = mock(RepositorioAdministrador.class);
        _servicioAdministrador = new ServicioAdministradorImpl(_repositorioAdministrador);

        _repositorioMedico = mock(RepositorioMedico.class);
        _servicioMedico = new ServicioMedicoImpl(_repositorioMedico);

        _repositorioPaciente = mock(RepositorioPaciente.class);
        _repositorioRegistroUsuario = mock(RepositorioRegistroUsuario.class);
        _servicioRegistroUsuario = new ServicioRegistroUsuarioImpl(_repositorioRegistroUsuario,
                                                                   _repositorioAdministrador,
                                                                   _repositorioPaciente,
                                                                   _repositorioMedico);
    }

    @Test()
    public void seObtienenCitasDelDia(){
        Usuario medico = givenUsuarioMedico();
        List<Cita> citas = whenBuscoCitasDelDia(medico);
        thenObtengoLaCitaDelDia(citas);
    }

    @Test(expected = EspecialidadRepetida.class)
    public void noSePuedeAgregarEspecialidadRepetida() {

        Usuario medico = givenUsuarioMedico();

        whenEspecialidadNuevaEsRepetida(medico);

    }

    @Test
    public void testQueUnMedicoPuedaVerSuAgenda(){
        Usuario medico = givenUsuarioMedico();
        List<Agenda> agenda = whenBuscoLaAgenda(medico.getEmail());
        thenLaAgendaDaExisto(agenda);
    }

    @Test
    public void testQueUnMedicoPuedaAgregarUndiaASuAgenda(){
        Usuario medico = givenUsuarioMedico();
        Agenda agenda = whenCreoUnDiaEnLaAgenda(medico);
        thenLaAgendaDaSeCreaConExito(agenda, medico);
    }

    private void thenLaAgendaDaSeCreaConExito(Agenda agenda, Usuario medico) {
        when(_repositorioMedico.obtenerAgenda(medico.getEmail())).thenReturn(Arrays.asList(agenda));
        _servicioMedico.agregarDiaAgenda(agenda);
        List<Agenda> agendaList =_servicioMedico.getAgenda(medico.getEmail());

        assertThat(agendaList).isNotNull();
        assertThat(agendaList.contains(agenda)).isTrue();
    }

    private Agenda whenCreoUnDiaEnLaAgenda(Usuario medico) {
        Calendar calendar = Calendar.getInstance();
        Date horaDesde = calendar.getTime();
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 2);
        Date horaHasta = calendar.getTime();

        Agenda agenda = new Agenda();
        agenda.setMedico(medico);
        agenda.setDia("Lunes");
        agenda.setActivo(true);
        agenda.setHoraDesde(horaDesde);
        agenda.setHoraHasta(horaHasta);

        return agenda;
    }

    private void thenLaAgendaDaExisto(List<Agenda> agenda) {
        assertThat(agenda.size()).isEqualTo(2);
    }

    private List<Agenda> whenBuscoLaAgenda(String email) {
        List<Agenda> agenda = Arrays.asList(new Agenda(), new Agenda());
        when(_repositorioMedico.obtenerAgenda(email)).thenReturn(agenda);

        return _servicioMedico.getAgenda(email);
    }

    private void whenEspecialidadNuevaEsRepetida(Usuario medico) throws EspecialidadRepetida{

        when(_repositorioMedico.addEspecialidad( medico, 1)).thenReturn(true).thenReturn(false);

        _servicioMedico.addEspecialidad(medico, 1);
        _servicioMedico.addEspecialidad(medico, 1); // Debe disparar  Excepcion
    }

    private Usuario givenUsuarioMedico() {

        Usuario medico = new Usuario();
        medico.setEmail("pepe@pepe.com");
        medico.setRol("Medico");

        return  medico;
    }

    private List<Cita> whenBuscoCitasDelDia(Usuario medico) {
        List<Cita> citas = new ArrayList<>();
        Cita citaHoy2 = new Cita();
        Cita citaHoy = new Cita();
        Date hoy = new Date();
        citaHoy.setFecha(hoy);
        citaHoy2.setFecha(hoy);
        citas.add(citaHoy);
        citas.add(citaHoy2);

        when(_repositorioMedico.obtenerCitasPorFecha(anyString(), any())).thenReturn(citas);

        List<Cita> returnCitas = _servicioMedico.obtenerCitasDelDia(medico.getEmail());

        return returnCitas;

    }

    private void thenObtengoLaCitaDelDia(List<Cita> citas) {
        Date hoy = new Date();

        for(Cita cita : citas){
            assertThat(cita.getFecha().equals(hoy));
        }
    }

}
