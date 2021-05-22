package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;
import java.util.*;

@Entity
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne()
    @JoinColumn(name = "paciente_id")
    private Usuario paciente;
    @OneToOne()
    @JoinColumn(name = "medico_id")
    private Usuario medico;
    @OneToOne()
    @JoinColumn(name = "especialidad_id")
    private Especialidad especialidad;
    @OneToOne()
    @JoinColumn(name = "tipoCita_id")
    private TipoCita tipoCita;
    @Temporal(TemporalType.DATE)
    private Calendar fecha;
    @Temporal(TemporalType.TIME)
    private Date hora;
    @OneToMany(mappedBy = "cita")
    private List<CitaHistoria> historias;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar created_at;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updated_at;

    public Cita() {
        this.historias = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getPaciente() {
        return paciente;
    }

    public void setPaciente(Usuario paciente) {
        this.paciente = paciente;
    }

    public Usuario getMedico() {
        return medico;
    }

    public void setMedico(Usuario medico) {
        this.medico = medico;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public List<CitaHistoria> getHistorias() {
        return historias;
    }

    public void setHistorias(List<CitaHistoria> historias) {
        this.historias = historias;
    }

    public TipoCita getTipoCita() {
        return tipoCita;
    }

    public void setTipoCita(TipoCita tipoCita) {
        this.tipoCita = tipoCita;
    }

    public Calendar getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Calendar created_at) {
        this.created_at = created_at;
    }

    public Calendar getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Calendar updated_at) {
        this.updated_at = updated_at;
    }
}
