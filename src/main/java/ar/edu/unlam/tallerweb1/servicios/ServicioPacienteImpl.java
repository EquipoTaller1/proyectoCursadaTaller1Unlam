package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.Excepciones.ErrorCita;
import ar.edu.unlam.tallerweb1.Excepciones.ErrorEnFormatoDeFechaException;
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

    @Override
    public Cita getCita(String email, long idCita)throws RuntimeException{

        Cita cita = repositorioCita.obtenerCita(idCita);

        if (cita == null)
            throw new ErrorCita("La cita no existe");
        if (cita.getPaciente().getEmail().equals(email))
            return cita;
        else
            throw new ErrorCita("La cita no pertenece al usuario logueado");
    }

    @Override
    public Cita getCita(long idCita)throws RuntimeException{

        Cita cita = repositorioCita.obtenerCita(idCita);

        if (cita == null)
            throw new ErrorCita("La cita no existe");

        return cita;

    }

}
