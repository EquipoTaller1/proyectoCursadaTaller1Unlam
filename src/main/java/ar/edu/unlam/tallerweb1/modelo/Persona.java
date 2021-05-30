package ar.edu.unlam.tallerweb1.modelo;

import ar.edu.unlam.tallerweb1.Excepciones.FaltanDatosParaElRegistroException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String tipoDocumento;
    private String numeroDocumento;
    private String email;
    @Temporal(TemporalType.DATE)
    private Calendar fechaNacimiento;
    private String sexo;
    private String numeroAfiliado;
    @OneToOne(mappedBy = "persona")
    @JsonIgnore
    private Usuario usuario;
    private String matricula;





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Calendar getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date date) {
        this.fechaNacimiento = Calendar.getInstance();
        this.fechaNacimiento.setTime(date);
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNumeroAfiliado() {
        return numeroAfiliado;
    }

    public void setNumeroAfiliado(String numeroAfiliado) {
        this.numeroAfiliado = numeroAfiliado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public boolean chequearPersona(Persona persona){

        boolean datosCorrectos = true;

        if (this.nombre == null ||
                this.apellido == null ||
                this.fechaNacimiento == null ||
                this.numeroAfiliado == null ||
                this.numeroDocumento == null ||
                this.tipoDocumento == null)
        {
            datosCorrectos = false;
        }

        return datosCorrectos;

    }


}
