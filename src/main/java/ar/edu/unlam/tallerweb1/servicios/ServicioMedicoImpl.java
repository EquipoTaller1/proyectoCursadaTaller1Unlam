package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.Excepciones.EspecialidadRepetida;
import ar.edu.unlam.tallerweb1.Excepciones.ObtenerCitasDelDiaException;
import ar.edu.unlam.tallerweb1.modelo.Cita;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroMedico;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

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
          Date hoy = new Date();
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

}
