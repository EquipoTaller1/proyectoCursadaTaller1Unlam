package ar.edu.unlam.tallerweb1.Excepciones;

public class PersonaYaExisteException extends RuntimeException{
    public PersonaYaExisteException(String message) {
        super(message);
    }
}
