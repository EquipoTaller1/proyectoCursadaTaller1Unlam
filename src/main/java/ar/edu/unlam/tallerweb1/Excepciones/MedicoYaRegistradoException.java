package ar.edu.unlam.tallerweb1.Excepciones;

public class MedicoYaRegistradoException extends RuntimeException {
    public MedicoYaRegistradoException(String message) {
        super(message);
    }
}
