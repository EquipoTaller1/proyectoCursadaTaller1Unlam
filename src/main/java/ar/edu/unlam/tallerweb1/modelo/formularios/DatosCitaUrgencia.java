package ar.edu.unlam.tallerweb1.modelo.formularios;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public class DatosCitaUrgencia {
    private String paciente;

    private int medico;

    @NotNull(message = "Tenes que seleccionar una especialidad")
    private int especialidad;

    private LocalTime hora;
    private int tipoCita;

    private LocalDate fecha;

    private String detallesDePedidoDeUrgencia;

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setMedico(int medico) {
        this.medico = medico;
    }

    public int getMedico() {
        return medico;
    }

    public void setEspecialidad(int especialidad) {
        this.especialidad = especialidad;
    }

    public int getEspecialidad() {
        return especialidad;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setTipoCita(int tipoCita) {
        this.tipoCita = tipoCita;
    }

    public int getTipoCita() {
        return tipoCita;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getDetallesDePedidoDeUrgencia() {
        return detallesDePedidoDeUrgencia;
    }

    public void setDetallesDePedidoDeUrgencia(String detallesDePedidoDeUrgencia) {
        this.detallesDePedidoDeUrgencia = detallesDePedidoDeUrgencia;
    }
}
