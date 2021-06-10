package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Cita;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroMedico;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface ServicioMedico {

    Persona consultarMedico(String matricula);

    Usuario consultarMedicoPorEmail(String email);

    void registrarMedico(FormularioRegistroMedico formulario, Persona persona);

    List<Cita> obtenerCitasDelDia(String email);

    void addEspecialidad(Usuario medico, int especialidad);

}
