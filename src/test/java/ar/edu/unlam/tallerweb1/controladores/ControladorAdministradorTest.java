package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioPersona;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioAdministrador;
import ar.edu.unlam.tallerweb1.servicios.ServicioAdministrador;
import ar.edu.unlam.tallerweb1.servicios.ServicioAdministradorImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;


import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class ControladorAdministradorTest {

    private RepositorioAdministrador repositorioPersona;
    private ServicioAdministrador servicioAdministrador;
    private ControladorAdministrador controladorAdministrador;

    @Before
    public void init() {
        repositorioPersona = mock(RepositorioAdministrador.class);
        servicioAdministrador = new ServicioAdministradorImpl(repositorioPersona);
        controladorAdministrador = new ControladorAdministrador(servicioAdministrador);
    }

    @Test
    @Transactional
    @Rollback
    public void testIrARegistrarPersona() {
        ModelAndView mav = controladorAdministrador.irARegistrarPersona();

        assertThat(mav.getViewName()).isEqualTo("administrador/registrar-persona");
        assertThat(mav.getModel().get("persona")).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void testRegistrarPersona() {
        FormularioPersona persona = givenDatosDePersonaCorrectos();
        ModelAndView modelo = whenSeEnvianDatosParaRegistrar(persona);
        thenSeVuelveALaVistaDeRegistroDePersona(modelo);
    }

    private FormularioPersona givenDatosDePersonaCorrectos() {
        FormularioPersona persona = new FormularioPersona();

        persona.setNumeroAfiliado("20210525001");
        persona.setNombre("Pepe");
        persona.setApellido("Argento");
        persona.setEmail("nherrera3276@gmail.com");
        persona.setTipoDocumento("DNI");
        persona.setNumeroDocumento("4836646");
        persona.setFechaNacimiento("03/03/2021");
        persona.setSexo("Otre");

        return persona;
    }


    private ModelAndView whenSeEnvianDatosParaRegistrar(FormularioPersona persona) {
        BindingResult result = new DataBinder(null).getBindingResult();

        return controladorAdministrador.registrarPersona(persona, result);
    }

    private void thenSeVuelveALaVistaDeRegistroDePersona(ModelAndView model) {
        assertThat(model.getViewName()).isEqualTo("administrador/registrar-persona");
        assertThat(model.getModel().get("persona")).isNotNull();
        assertThat(model.getModel().get("exito")).isEqualTo("La persona se registró correctamente");
    }

}
