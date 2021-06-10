package ar.edu.unlam.tallerweb1.modelo.formularios;

import ar.edu.unlam.tallerweb1.modelo.Especialidad;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class FormularioEspecialidades {

        private Usuario usuario;

        @NotNull(message = "El campo especialidad es obligatorio")
        private int especialidadNueva;

        List<Especialidad> especialidadesTodas;

    public List<Especialidad> getEspecialidadesTodas() {
        return especialidadesTodas;
    }

    public void setEspecialidadesTodas(List<Especialidad> especialidadesTodas) {
        this.especialidadesTodas = especialidadesTodas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getEspecialidadNueva() {
        return especialidadNueva;
    }

    public void setEspecialidadNueva(int especialidadNueva) {
        this.especialidadNueva = especialidadNueva;
    }
}



