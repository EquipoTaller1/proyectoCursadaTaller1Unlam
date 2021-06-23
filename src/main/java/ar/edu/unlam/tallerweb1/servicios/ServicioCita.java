package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Especialidad;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.DatosCita;
import ar.edu.unlam.tallerweb1.modelo.formularios.DatosCitaUrgencia;

import java.text.ParseException;
import java.util.List;

public interface ServicioCita {
    List<Especialidad> allEspecialidades();

    List<Usuario> medicosByEspecialidad(Long id);

    Usuario userById(Long id);

    void create(DatosCita datosCita) throws ParseException;

    void create(DatosCitaUrgencia datosCitaUrgencia) throws ParseException;
}
