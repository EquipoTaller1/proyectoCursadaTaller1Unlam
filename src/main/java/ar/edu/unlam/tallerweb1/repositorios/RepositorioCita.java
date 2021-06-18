package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Cita;
import ar.edu.unlam.tallerweb1.modelo.Especialidad;
import ar.edu.unlam.tallerweb1.modelo.TipoCita;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface RepositorioCita {
    List<Especialidad> allEspecialidad();

    List<Cita> obtenerCitas(String email);

    List<Cita> obtenerCitasProximas(String email);

    Cita guardarCita(Date fecha, Date hora, Especialidad especialidad, TipoCita tipoCita, Usuario medico, Usuario paciente);

    Cita guardarCita(Date fecha, Date hora, Especialidad especialidad, TipoCita tipoCita, Usuario medico, Usuario paciente, String detallesDePedidoDeUrgencia);

    Cita obtenerCita(Long idCita);

    List<Usuario> medicoByEspecialidad(Long id);

    Especialidad especialidadById(Long id);

    Usuario userById(Long id);

    TipoCita tipoCitaById(Long id);

    Usuario userByEmail(String email);
}
