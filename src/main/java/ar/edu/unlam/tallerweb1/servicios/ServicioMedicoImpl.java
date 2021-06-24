package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.Excepciones.ErrorEspecialidad;
import ar.edu.unlam.tallerweb1.Excepciones.EspecialidadRepetida;
import ar.edu.unlam.tallerweb1.Excepciones.ObtenerCitasDelDiaException;
import ar.edu.unlam.tallerweb1.modelo.Agenda;
import ar.edu.unlam.tallerweb1.modelo.Cita;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroMedico;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ServicioMedicoImpl implements ServicioMedico {

    RepositorioMedico repositorioMedico;

    @Autowired
    public ServicioMedicoImpl(RepositorioMedico repositorioMedico) {
        this.repositorioMedico = repositorioMedico;
    }

    @Override
    public Persona consultarMedico(String matricula) {
        return null;
    }

    @Override
    public void registrarMedico(FormularioRegistroMedico formulario, Persona persona) {

    }

    @Override
    public List<Cita> obtenerCitasDelDia(String email) throws ObtenerCitasDelDiaException
    {

      try {
          LocalDate hoy = LocalDate.now();
          return repositorioMedico.obtenerCitasPorFecha(email, hoy);
      }catch (Exception e)
      {
          throw new ObtenerCitasDelDiaException("Error al obtener citas del día");
      }

    }

    @Override
    public Usuario consultarMedicoPorEmail(String email){
        return repositorioMedico.obtenerMedicoPorEmail(email);
    }

    @Override
    public void addEspecialidad(Usuario medico, int especialidad) throws EspecialidadRepetida {
        if (!repositorioMedico.addEspecialidad(medico, especialidad))
            throw new EspecialidadRepetida("Ya tiene registrada esa especialidad");
    }

    @Override
    public void deleteEspecilidad(String emailMedico, long especialidad) throws ErrorEspecialidad {
        if (!repositorioMedico.deleteEspecialidad(emailMedico, especialidad))
            throw new ErrorEspecialidad("No se puede borrar la especialidad");
    }

    @Override
    public List<Agenda> getAgenda(String email) {
        return repositorioMedico.obtenerAgenda(email);
    }

    @Override
    public void agregarDiaAgenda(Agenda agenda) {

    }

    @Override
    public void actualizarAgenda(Agenda agenda, String username) {
        agenda.setMedico(this.consultarMedicoPorEmail(username));
        if (agenda.getActivo() == null) {
            agenda.setActivo(false);
        } else {
            agenda.setActivo(true);
        }

        this.repositorioMedico.actualizarAgenda(agenda);
    }

    @Override
    public List<String> getHorariosDia(Long medico, String fecha) {
        DateTimeFormatter formatoDia = DateTimeFormatter
                .ofPattern("EEEE")
                .withLocale(new Locale("es", "AR"));
        LocalDate fechaLocal = LocalDate.parse(fecha);
        String dia = fechaLocal.format(formatoDia);

        Agenda agenda = this.repositorioMedico.getDiaAgenda(medico, dia);
        List<String> intervalos = new ArrayList<>();
        if (!agenda.getActivo()){
            return intervalos;
        }

        LocalTime interIni = agenda.getHoraDesde();
        LocalTime interFin = agenda.getHoraHasta();
        intervalos.add(interIni.toString());

        List<Cita> citasDeLaFecha = this.repositorioMedico.obtenerCitasPorFechaMedicoId(medico, fechaLocal);
        List<String> horariosNoDisponibles = new ArrayList<>();
        for (Cita c:citasDeLaFecha){
            horariosNoDisponibles.add(c.getHora().toString());
        }

        while (interIni.isBefore(interFin)){
            interIni = interIni.plusMinutes(40);
            if (!horariosNoDisponibles.contains(interIni.toString()))
                intervalos.add(interIni.toString());
        }

        return intervalos;
    }

}
