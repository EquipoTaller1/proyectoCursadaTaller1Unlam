package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;
    private String matricula;
    private int edad;
    private String sexo;
    private String especialidad;
    private float lat_actual;
    private float long_actual;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getId_usuario() {
        return usuario;
    }

    public void setId_usuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
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

