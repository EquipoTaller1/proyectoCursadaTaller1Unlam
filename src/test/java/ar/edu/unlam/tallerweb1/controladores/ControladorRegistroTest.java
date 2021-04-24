package ar.edu.unlam.tallerweb1.controladores;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.Assertions.*;

public class ControladorRegistroTest {
    private ControladorRegistro controladorRegistro = new ControladorRegistro();

    @Test
    public void testIrARegistro(){
        ModelAndView mav = controladorRegistro.irARegistro("");

        assertThat(mav.getViewName()).isEqualTo("auth/register");
        assertThat(mav.getModel().get("usuario")).isNotNull();
    }
}
