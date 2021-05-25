package ar.edu.unlam.tallerweb1.modelo.formularios;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;
import javax.validation.constraints.NotBlank;

public class formularioPersona {

    @NotBlank(message = "El campo Nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El campo Apellido es obligatorio")
    private String apellido;
    @NotBlank(message = "El campo Tipo de documento es obligatorio")
    private String tipoDocumento;
    @NotBlank(message = "El campo Número de documento es obligatorio")
    private String numeroDocumento;
    @NotBlank(message = "El campo Fecha de nacimiento es obligatorio")
    @Temporal(TemporalType.DATE)
    private Calendar fechaNacimiento;
    @NotBlank(message = "El campo Sexo es obligatorio")
    private String sexo;
    @NotBlank(message = "El campo Número de afiliado es obligatorio")
    private String numeroAfiliado;
    private String matricula;

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

    public void setFechaNacimiento(Calendar fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
