package ar.edu.unlam.tallerweb1.controladores;
import ar.edu.unlam.tallerweb1.modelo.formularios.formularioPersona;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.servlet.ModelAndView;
import java.util.Calendar;
import static org.assertj.core.api.Assertions.*;

public class ControladorAdministradorTest {

    private ControladorAdministrador controladorAdministrador;

    @Before
    public void init(){
        controladorAdministrador = new ControladorAdministrador();
    }

    @Test
    public void testIrARegistrarPersona(){
        ModelAndView mav = controladorAdministrador.irARegistrarPersona();

        assertThat(mav.getViewName()).isEqualTo("administrador/registrar-persona");
        assertThat(mav.getModel().get("persona")).isNotNull();
    }

    @Test @Rollback
    public void testRegistrarPersona(){
        formularioPersona persona = givenDatosDePersonaCorrectos();
        ModelAndView modelo = whenSeEnvianDatosParaRegistrar(persona);
        thenSeVuelveALaVistaDeRegistroDePersona(modelo);
    }

    private formularioPersona givenDatosDePersonaCorrectos(){
        formularioPersona persona = new formularioPersona();

        persona.setNombre("Pepe");
        persona.setApellido("Argento");
        persona.setFechaNacimiento(Calendar.getInstance());
        persona.setNumeroAfiliado("20210525001");
        persona.setNumeroDocumento("4836646");
        persona.setSexo("Otre");
        persona.setTipoDocumento("DNI");

        return persona;
    }

    private ModelAndView whenSeEnvianDatosParaRegistrar(formularioPersona persona) {
        BindingResult result = new DataBinder(null).getBindingResult();

        return controladorAdministrador.registrarPersona(persona, result);
    }

    private void thenSeVuelveALaVistaDeRegistroDePersona(ModelAndView model){
        assertThat(model.getViewName()).isEqualTo("administrador/registrar-persona");
        assertThat(model.getModel().get("persona")).isNotNull();
        assertThat(model.getModel().get("exito")).isEqualTo("La persona se registró correctamente");
    }

}
