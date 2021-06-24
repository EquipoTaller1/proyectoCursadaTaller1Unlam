package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.DatosCitaUrgencia;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioCita;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioMedico;
import ar.edu.unlam.tallerweb1.servicios.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControladorPacienteTest {

    private ServicioCita servicioCita;
    private RepositorioCita repositorioCita;
    private ServicioPaciente servicioPaciente;
    private ControladorPaciente controladorPaciente;
    private Authentication authentication;
    private BindingResult bindingResult;


    @Before
    public void init() {
        repositorioCita = mock(RepositorioCita.class);
        authentication = mock(Authentication.class);
        bindingResult = mock(BindingResult.class);
        servicioCita = new ServicioCitaImpl(repositorioCita);
        controladorPaciente = new ControladorPaciente(servicioPaciente, servicioCita);
    }


    @Test
    public void sePuedeIrACrearUnaCitaDeUrgencia() {
        ModelAndView mav = controladorPaciente.irACrearCitaUrgencia();
        assertThat(mav.getViewName()).isEqualTo("mis-citas/createUrgencia");
    }

    /*@Test
    public void sePuedeCrearUnaCitaDeUrgencia() throws ParseException {
        DatosCitaUrgencia datosCitaUrgencia = givenDatosDeCitaUrgenciaCorrectos();

        whenQuieroRegistrarLaCitaDeUrgencia(datosCitaUrgencia);

        verify(servicioCita).create(datosCitaUrgencia);
    }*/



    private void whenQuieroRegistrarLaCitaDeUrgencia(DatosCitaUrgencia datosCitaUrgencia) {
          controladorPaciente.createUrgencia(datosCitaUrgencia, bindingResult, authentication);
    }

    private DatosCitaUrgencia givenDatosDeCitaUrgenciaCorrectos() {
        DatosCitaUrgencia datosCitaUrgencia = new DatosCitaUrgencia();
        datosCitaUrgencia.setEspecialidad(1);
        datosCitaUrgencia.setDetallesDePedidoDeUrgencia("me duele la muela");

        return datosCitaUrgencia;
    }


}
