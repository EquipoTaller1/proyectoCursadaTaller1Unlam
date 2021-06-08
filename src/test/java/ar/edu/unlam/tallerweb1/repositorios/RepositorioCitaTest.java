package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Cita;
import ar.edu.unlam.tallerweb1.modelo.Especialidad;
import ar.edu.unlam.tallerweb1.modelo.TipoCita;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.DatosCita;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositorioCitaTest extends SpringTest {

    @Autowired
    RepositorioCita repositorioCita;

    @Test
    @Transactional
    @Rollback
    public void testQueUnPacienteCreeUnaCita() throws ParseException {
        DatosCita datosCita = givenDatosDeCitas();

        Cita cita = whenCreoUnaCita(datosCita);

        thenObtengoLaCita(cita);
    }

    private DatosCita givenDatosDeCitas() {
        DatosCita datosCita = new DatosCita();
        datosCita.setPaciente("paciente@correo.com");
        datosCita.setMedico(1);
        datosCita.setEspecialidad(1);
        datosCita.setTipoCita(1);
        datosCita.setFecha("2021/06/25");
        datosCita.setHora("10:00");

        return datosCita;
    }

    private Cita whenCreoUnaCita(DatosCita datosCita) throws ParseException {
        Usuario paciente = new Usuario();
        Usuario medico = new Usuario();
        Especialidad especialidad = new Especialidad();
        TipoCita tipoCita = new TipoCita();
        Date fecha = new Date(datosCita.getFecha());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm");
        Date hora = simpleDateFormat.parse(datosCita.getHora());


        return repositorioCita.guardarCita(fecha, hora, especialidad, tipoCita, medico, paciente);
    }

    private void thenObtengoLaCita(Cita cita) {
        assertThat(cita.getId()).isNotNull();
        assertThat(cita.getHistorias().size()).isEqualTo(1);
    }
}
