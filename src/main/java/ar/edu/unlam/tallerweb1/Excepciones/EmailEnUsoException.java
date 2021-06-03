package ar.edu.unlam.tallerweb1.Excepciones;

public class EmailEnUsoException extends RuntimeException {

    public EmailEnUsoException(String message) {
        super(message);
    }
}
