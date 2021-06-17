package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.Excepciones.EspecialidadRepetida;
import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Cita;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

import ar.edu.unlam.tallerweb1.repositorios.RepositorioAdministrador;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioMedico;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPaciente;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioRegistroUsuario;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
