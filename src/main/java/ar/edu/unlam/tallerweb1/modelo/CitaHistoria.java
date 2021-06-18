package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;
import java.util.Calendar;

@Entity
public class CitaHistoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "cita_id")
    private Cita cita;
    @ManyToOne()
    @JoinColumn(name = "estado_id")
    private Estado estado;
    private String observacion;
    private String archivo;


    @Temporal(TemporalType.TIMESTAMP)
    private Calendar created_at;

    @Column(name = "detalles_de_pedido_de_urgencia")
    private String detallesDePedidoDeUrgencia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Calendar getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Calendar created_at) {
        this.created_at = created_at;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getDetallesDePedidoDeUrgencia() {
        return detallesDePedidoDeUrgencia;
    }

    public void setDetallesDePedidoDeUrgencia(String detallesDePedidoDeUrgencia) {
        this.detallesDePedidoDeUrgencia = detallesDePedidoDeUrgencia;
    }

}
