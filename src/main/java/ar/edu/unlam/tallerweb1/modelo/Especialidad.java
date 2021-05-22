package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;
    @ManyToMany()
    @JoinTable(
            name = "especialidad_medico",
            joinColumns = {@JoinColumn(name = "especialidad_id")},
            inverseJoinColumns = {@JoinColumn(name = "medico_id")}
    )
    private Set<Usuario> medicos;

    public Especialidad() {
        this.medicos = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
