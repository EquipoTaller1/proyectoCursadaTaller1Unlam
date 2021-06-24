package ar.edu.unlam.tallerweb1.modelo;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy = "cita")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<CitaHistoria> historias;
    private LocalDate fecha;
    private LocalTime hora;
    private LocalDateTime created_at;
    private float latitud;
    private float longitud;

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

    public LocalDate getFecha() { return fecha; }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
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

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public CitaHistoria getUltimaHistoria() {
        return getHistorias().get(this.getHistorias().size() - 1);
    }

    public String fechaFormateada(){
        DateTimeFormatter esDateFormatLargo = DateTimeFormatter
                .ofPattern("EEEE, dd 'de' MMMM 'de' yyyy")
                .withLocale(new Locale("es", "AR"));

        return fecha.format(esDateFormatLargo);
    }

    public void agregarHistoria(CitaHistoria citaHistoria){
        citaHistoria.setCita(this);
        this.getHistorias().add(citaHistoria);
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
