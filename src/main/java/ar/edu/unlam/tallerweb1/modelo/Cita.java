package ar.edu.unlam.tallerweb1.modelo;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Usuario paciente;
    @ManyToOne()
    @JoinColumn(name = "medico_id")
    private Usuario medico;
    @ManyToOne()
    @JoinColumn(name = "especialidad_id")
    private Especialidad especialidad;
    @ManyToOne()
    @JoinColumn(name = "tipoCita_id")
    private TipoCita tipoCita;
    @Temporal(TemporalType.DATE)
    private Calendar fecha;
    @Temporal(TemporalType.TIME)
    private Date hora;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cita")
    @Fetch(value = FetchMode.SUBSELECT)
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

    public Calendar getFecha() { return fecha; }

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

    public CitaHistoria getUltimaHistoria() {
        return getHistorias().get(this.getHistorias().size() - 1);
    }

    public String fechaHoraFormateada(){
        fecha.set(Calendar.HOUR_OF_DAY, this.hora.getHours());
        fecha.set(Calendar.MINUTE, this.hora.getMinutes());
        SimpleDateFormat fechaFormato = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        return fechaFormato.format(fecha.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cita)) return false;
        Cita cita = (Cita) o;
        return Objects.equals(getId(), cita.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
