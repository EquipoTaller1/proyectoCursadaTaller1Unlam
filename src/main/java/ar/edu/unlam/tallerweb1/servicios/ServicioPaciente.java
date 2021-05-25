package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Cita;

import java.util.List;

public interface ServicioPaciente {
    List<Cita> getCitas(String email);

    List<Cita> getCitasProximas(String email);
}
