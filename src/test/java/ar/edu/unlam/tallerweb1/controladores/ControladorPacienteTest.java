package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.repositorios.RepositorioPaciente;
import ar.edu.unlam.tallerweb1.servicios.ServicioPaciente;
import ar.edu.unlam.tallerweb1.servicios.ServicioPacienteImpl;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class ControladorPacienteTest {
    ControladorPaciente controladorPaciente;
    ServicioPaciente servicioPaciente;
    RepositorioPaciente repositorioPaciente;

    @Before
    public void init(){
        repositorioPaciente = mock(RepositorioPaciente.class);
        servicioPaciente = new ServicioPacienteImpl(repositorioPaciente);
        controladorPaciente = new ControladorPaciente(servicioPaciente);
    }

    @Test
    public void testQueElPacientePuedaVerSusProximasCitas(){

    }
}
