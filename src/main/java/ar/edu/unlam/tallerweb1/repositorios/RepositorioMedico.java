package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Agenda;
import ar.edu.unlam.tallerweb1.modelo.Cita;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroMedico;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface RepositorioMedico {

    List<Usuario> obtenerTodos();

    Persona consultarMedico(String matricula);

    void registrarMedico(FormularioRegistroMedico formulario, Persona persona);

    List<Cita> obtenerCitasPorFecha(String email, LocalDate fecha);

    Usuario obtenerMedicoPorEmail(String email);

    boolean addEspecialidad(Usuario medico, int especialidad);

    boolean deleteEspecialidad(String emailMedico, long especialidad);

    List<Agenda> obtenerAgenda(String email);

    void guardarAgenda(Agenda agenda);

    void actualizarAgenda(Agenda agenda);

    Agenda getDiaAgenda(Long medico, String dia);

    List<Cita> obtenerCitasPorFechaMedicoId(Long idMedico, LocalDate fecha);
}

