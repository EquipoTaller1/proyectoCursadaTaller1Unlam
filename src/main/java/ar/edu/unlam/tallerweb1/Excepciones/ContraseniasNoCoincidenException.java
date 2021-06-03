package ar.edu.unlam.tallerweb1.Excepciones;

public class ContraseniasNoCoincidenException extends RuntimeException {

    public ContraseniasNoCoincidenException(String message) {
        super(message);
    }
}
