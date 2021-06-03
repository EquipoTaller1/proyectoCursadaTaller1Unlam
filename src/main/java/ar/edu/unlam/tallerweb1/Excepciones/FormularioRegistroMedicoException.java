package ar.edu.unlam.tallerweb1.Excepciones;

import java.util.ArrayList;
import java.util.List;

public class FormularioRegistroMedicoException extends RuntimeException {
    private List<String> errores;

    public FormularioRegistroMedicoException(ArrayList<String> errores) {
        this.errores = errores;
    }

    public List<String> getErrores() {
        return errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }
}
