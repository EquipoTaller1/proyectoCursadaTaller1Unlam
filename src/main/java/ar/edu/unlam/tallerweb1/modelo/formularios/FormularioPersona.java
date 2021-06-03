package ar.edu.unlam.tallerweb1.modelo.formularios;


import ar.edu.unlam.tallerweb1.modelo.Persona;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.validation.constraints.NotBlank;

public class FormularioPersona {

    @NotBlank(message = "El campo Nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El campo Apellido es obligatorio")
    private String apellido;
    @NotBlank(message = "El campo Tipo de documento es obligatorio")
    private String tipoDocumento;
    @NotBlank(message = "El campo Número de documento es obligatorio")
    private String numeroDocumento;
    @NotBlank(message = "El campo Email de documento es obligatorio")
    private String email;

    @NotBlank
    private String fechaNacimiento;
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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
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

    public void setMatricula(String matricula) { this.matricula = matricula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Persona toPersona()
    {
        Persona persona = new Persona();
        persona.setNumeroAfiliado(numeroAfiliado);
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        persona.setFechaNacimiento(fechaNacimiento);
        persona.setNumeroDocumento(numeroDocumento);
        persona.setTipoDocumento(tipoDocumento);
        persona.setSexo(sexo);
        persona.setMatricula(matricula);
        persona.setEmail(email);


        return persona;
    }

    public boolean chequearFormulario(FormularioPersona formulario){
        if (
                        this.numeroAfiliado == null||
                        this.nombre == null ||
                        this.apellido == null ||
                        this.email == null ||
                        this.tipoDocumento == null ||
                        this.numeroDocumento == null ||
                        this.sexo == null
                )
        {
            return false;
        }
        return  true;
    }

    public boolean chequearFormulario(Persona persona){
        if (
                persona.getNumeroAfiliado().equals("")||
                        persona.getNombre().equals("") ||
                        persona.getApellido().equals("") ||
                        persona.getEmail().equals("") ||
                        persona.getTipoDocumento().equals("")||
                        persona.getNumeroDocumento().equals("")||
                        persona.getFechaNacimiento() == null ||
                        persona.getFechaNacimiento().equals("") ||
                        persona.getSexo().equals("")
        )
        {
            return false;
        }
        return  true;
    }
}
