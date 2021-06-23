package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Especialidad;
import ar.edu.unlam.tallerweb1.modelo.TipoCita;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.DatosCita;
import ar.edu.unlam.tallerweb1.modelo.formularios.DatosCitaUrgencia;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioCita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ServicioCitaImpl implements ServicioCita {

    RepositorioCita repositorioCita;

    @Autowired
    public ServicioCitaImpl(RepositorioCita repositorioCita){
        this.repositorioCita = repositorioCita;
    }

    @Override
    public List<Especialidad> allEspecialidades() {
        return repositorioCita.allEspecialidad();
    }

    @Override
    public List<Usuario> medicosByEspecialidad(Long id) {
        return repositorioCita.medicoByEspecialidad(id);
    }

    @Override
    public Usuario userById(Long id) {
        return repositorioCita.userById(id);
    }

    @Override
    public void create(DatosCita datosCita) throws ParseException {
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = simpleDateFormat2.parse(datosCita.getFecha());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date hora = simpleDateFormat.parse(datosCita.getHora());
        Especialidad especialidad = repositorioCita.especialidadById(Long.valueOf(datosCita.getEspecialidad()));
        TipoCita tipoCita = repositorioCita.tipoCitaById(Long.valueOf(datosCita.getTipoCita()));
        Usuario medico = repositorioCita.userById(Long.valueOf(datosCita.getMedico()));
        Usuario paciente = repositorioCita.userByEmail(datosCita.getPaciente());

        repositorioCita.guardarCita(fecha, hora, especialidad, tipoCita, medico, paciente);
    }

    @Override
    public void create(DatosCitaUrgencia datosCitaUrgencia) throws ParseException {
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = simpleDateFormat2.parse(datosCitaUrgencia.getFecha());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date hora = simpleDateFormat.parse(datosCitaUrgencia.getHora());
        Especialidad especialidad = repositorioCita.especialidadById(Long.valueOf(datosCitaUrgencia.getEspecialidad()));
        TipoCita tipoCita = repositorioCita.tipoCitaById(Long.valueOf(datosCitaUrgencia.getTipoCita()));
        Usuario medico = repositorioCita.userById(Long.valueOf(datosCitaUrgencia.getMedico()));
        Usuario paciente = repositorioCita.userByEmail(datosCitaUrgencia.getPaciente());
        String detallesDePedidoDeUrgencia = datosCitaUrgencia.getDetallesDePedidoDeUrgencia();

        repositorioCita.guardarCita(fecha, hora, especialidad, tipoCita, medico, paciente, detallesDePedidoDeUrgencia);
    }


}
