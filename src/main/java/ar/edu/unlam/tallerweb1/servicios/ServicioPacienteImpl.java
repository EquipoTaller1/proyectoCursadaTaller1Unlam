package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Cita;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioCita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ServicioPacienteImpl implements ServicioPaciente{

    private RepositorioCita repositorioCita;

    @Autowired
    public ServicioPacienteImpl(RepositorioCita repositorioCita){
        this.repositorioCita = repositorioCita;
    }



    @Override
    public List<Cita> getCitas(String email) {
        return repositorioCita.obtenerCitas(email);
    }

    @Override
    public List<Cita> getCitasProximas(String email) {
        return repositorioCita.obtenerCitasProximas(email);
    }
}
