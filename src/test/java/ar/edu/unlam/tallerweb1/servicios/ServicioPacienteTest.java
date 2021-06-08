package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Cita;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioCita;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioPacienteTest {
    ServicioPaciente servicioPaciente;
    RepositorioCita repositorioCita;

    @Before
    public void init(){
        repositorioCita = mock(RepositorioCita.class);
        servicioPaciente = new ServicioPacienteImpl(repositorioCita);
    }

    @Test
    public void testQueElPacientePuedaVerSusCitas(){
        Usuario paciente = givenPacienteLogueado();

        List<Cita> citas = whenObtengoLasCitas(paciente);

        thenVerificoLaCantidadDeCitas(citas);
    }

    @Test
    public void testQueUnPacienteCreeUnaCitaProgramada(){

    }

    private Usuario givenPacienteLogueado() {
        Usuario paciente = new Usuario();
        paciente.setRol("Paciente");
        paciente.setEmail("paciente@correo.com");

        return paciente;
    }

    private List<Cita> whenObtengoLasCitas(Usuario paciente) {
        Cita cita1 = new Cita();
        Cita cita2 = new Cita();
        List<Cita> citas = new ArrayList();
        citas.add(cita1);
        citas.add(cita2);

        when(repositorioCita.obtenerCitas(paciente.getEmail())).thenReturn(citas);
        return servicioPaciente.getCitas(paciente.getEmail());
    }

    private void thenVerificoLaCantidadDeCitas(List<Cita> citas) {
        assertThat(citas.size()).isEqualTo(2);
    }
}
