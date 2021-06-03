package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroMedico;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
