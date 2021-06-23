package ar.edu.unlam.tallerweb1.Excepciones;

public class ErrorCita extends RuntimeException {
    public ErrorCita(String message) {
        super(message);
    }
}