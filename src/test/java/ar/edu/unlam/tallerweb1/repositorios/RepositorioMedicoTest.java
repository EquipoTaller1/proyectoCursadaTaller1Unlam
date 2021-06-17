package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Cita;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.tz.FixedDateTimeZone;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.*;

public class RepositorioMedicoTest extends SpringTest {

    @Autowired
    private  RepositorioMedico repositorioMedico;

    @Test
    @Transactional
    public void seObtienenCitasDeUnMedicoEnUnDiaEspecifico(){
        givenCitasDeUnMedicoEnElDia();
        List<Cita> citas = whenSeObtienenLasCitasDelDia();
        thenLasCitasObtenidasSonLasCorrectas(citas);
    }

    @Test
    @Transactional
    public void noSeObtienenCitasDelMedicoEnFechaAntigua(){
        String medico = givenMedicoExistente();
        List<Cita> citas = whenSeObtienenLasCitasDeFechaAntigua(medico);
        thenNoSeEncuentranCitas(citas);
    }

    private void thenNoSeEncuentranCitas(List<Cita> citas) {
        //assertThat(citas).hasSize(0);
    }


    private List<Cita> whenSeObtienenLasCitasDeFechaAntigua(String medico) {
        /*Calendar fecha = Calendar.getInstance();
        fecha.setTime(new Date("1900/01/01"));*/
        return repositorioMedico.obtenerCitasPorFecha(medico, new Date("1900/01/01"));
    }

    private String givenMedicoExistente() {
        return "dmaradona@gmail.com";
    }


    private void thenLasCitasObtenidasSonLasCorrectas(List<Cita> citas) {
       // assertThat(citas).hasSize(2);
    }

    private List<Cita> whenSeObtienenLasCitasDelDia() {
       /* Calendar fecha = Calendar.getInstance();
        fecha.setTime(new Date("2021/06/07"));*/
// Se insertaron datos previamente en la BD para esa fecha y ese médico
        return repositorioMedico.obtenerCitasPorFecha("dmaradona@gmail.com", new Date("2021/06/07"));
    }

    private void givenCitasDeUnMedicoEnElDia() {
// Falta desarrollar RepositorioCita para generar casos de prueba automaticamente
    }

}
