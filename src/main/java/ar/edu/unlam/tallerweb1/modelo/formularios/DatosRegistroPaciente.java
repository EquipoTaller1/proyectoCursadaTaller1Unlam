package ar.edu.unlam.tallerweb1.modelo.formularios;

import javax.validation.constraints.NotBlank;

public class DatosRegistroPaciente {

    @NotBlank(message = "El campo afiliado es obligatorio")
    private String afiliado;
    @NotBlank(message = "El campo email es obligatorio")
    private String email;
    @NotBlank(message = "El campo password es obligatorio")
    private String password;
    @NotBlank(message = "El campo repetir password es obligatorio")
    private String passwordRepet;

    public String getAfiliado() {
        return afiliado;
    }

    public void setAfiliado(String afiliado) {
        this.afiliado = afiliado;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPasswordRepet() {

        return passwordRepet;
    }

    public void setPasswordRepet(String passwordRepet) {

        this.passwordRepet = passwordRepet;
    }
}
