package ar.edu.unlam.tallerweb1.Excepciones;

public class MedicoNoExisteException extends RuntimeException {
    public MedicoNoExisteException(String message) {
        super(message);
    }
}
