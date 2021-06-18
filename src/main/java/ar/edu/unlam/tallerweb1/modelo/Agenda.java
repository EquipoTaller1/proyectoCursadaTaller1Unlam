package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIME)
    private Date horaHasta;
    @Temporal(TemporalType.TIME)
    private Date horaDesde;
    @ManyToOne
    private Usuario medico;
    private String dia;
    private boolean activo;

    public void setHoraHasta(Date horaHasta) {
        this.horaHasta = horaHasta;
    }

    public void setMedico(Usuario medico) {
        this.medico = medico;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public void setActivo(boolean b) {
        this.activo = b;
    }

    public void setHoraDesde(Date horaDesde) {
        this.horaDesde = horaDesde;
    }
}
