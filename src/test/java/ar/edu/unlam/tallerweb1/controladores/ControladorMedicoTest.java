package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.repositorios.RepositorioAdministrador;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioMedico;
import ar.edu.unlam.tallerweb1.servicios.ServicioAdministrador;
import ar.edu.unlam.tallerweb1.servicios.ServicioAdministradorImpl;
import ar.edu.unlam.tallerweb1.servicios.ServicioMedico;
import ar.edu.unlam.tallerweb1.servicios.ServicioMedicoImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ControladorMedicoTest {
    private Authentication authentication;
    private RepositorioMedico repositorioMedico;
    private ServicioMedico servicioMedico;
    private ControladorMedico controladorMedico;

    @Before
    public void init() {
        authentication = mock(Authentication.class);
        repositorioMedico = mock(RepositorioMedico.class);
        servicioMedico = new ServicioMedicoImpl(repositorioMedico);
        controladorMedico = new ControladorMedico(servicioMedico);
    }

    @Test
    @Transactional
    @Rollback
    public void testIrARegistrarPersona() {
/*        ModelAndView mav = controladorMedico.irAMisCitas(authentication);
        assertThat(mav.getViewName()).isEqualTo("medico/citas-del-dia");
        assertThat(mav.getModel().get("citas")).isNotNull();*/
    }

}
