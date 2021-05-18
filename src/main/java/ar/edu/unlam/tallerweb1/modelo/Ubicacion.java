package ar.edu.unlam.tallerweb1.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Ubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private Usuario usuario;
    private float lat_actual;
    private float long_actual;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public float getLat_actual() {
        return lat_actual;
    }

    public void setLat_actual(float lat_actual) {
        this.lat_actual = lat_actual;
    }

    public float getLong_actual() {
        return long_actual;
    }

    public void setLong_actual(float long_actual) {
        this.long_actual = long_actual;
    }
}
