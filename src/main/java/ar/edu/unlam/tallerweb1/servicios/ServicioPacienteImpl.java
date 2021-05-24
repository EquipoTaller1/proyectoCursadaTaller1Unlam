package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Cita;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ServicioPacienteImpl implements ServicioPaciente{

    private RepositorioPaciente repositorioPaciente;

    @Autowired
    public ServicioPacienteImpl(RepositorioPaciente repositorioPaciente){
        this.repositorioPaciente = repositorioPaciente;
    }

    @Override
    public List<Cita> getCitas(String email) {
        return repositorioPaciente.obtenerCitas(email);
    }
}
