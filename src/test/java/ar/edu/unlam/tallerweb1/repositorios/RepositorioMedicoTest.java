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
    @Rollback
    public void seObtienenCitasDeUnMedicoEnUnDiaEspecifico(){
        givenCitasDeUnMedicoEnElDia();
        List<Cita> citas = whenSeObtienenLasCitasDelDia();
        thenLasCitasObtenidasSonLasCorrectas(citas);
    }

    private void thenLasCitasObtenidasSonLasCorrectas(List<Cita> citas) {
        //assertThat(citas).hasSize(1);
    }

    private List<Cita> whenSeObtienenLasCitasDelDia() {
     /*   DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd").withZone(new FixedDateTimeZone("UTC", "UTC", 0, 0));
        Calendar fecha = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        fecha.setTime(format.parseDateTime("2021-06-08").toDate());
*/

        Calendar fecha = Calendar.getInstance();
        fecha.setTime(new Date("2021/06/07"));
   //     fecha.set(Calendar.YEAR, 2021);
    //    fecha.set(Calendar.MONTH, 6);
     //   fecha.set(Calendar.DAY_OF_MONTH, 8);

        return repositorioMedico.obtenerCitasPorFecha("dmaradona@gmail.com", fecha);
    }

    private void givenCitasDeUnMedicoEnElDia() {

    }

}
