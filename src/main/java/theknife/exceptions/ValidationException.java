package theknife.exceptions;

/**
 * Classe ValidationException estende {@link Exception}, rappresenta
 * un'eccezione lanciata in caso di validazioni
 * fallite.
 * 
 * @author Alessio Sangiorgi 730420 VA
 */
public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
    }
}
