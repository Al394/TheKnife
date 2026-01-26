package theknife.exceptions;

/**
 * Classe AuthException estende {@link Exception}, rappresenta
 * un'eccezione lanciata in caso di autenticazioni fallite.
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class AuthException extends Exception {
    public AuthException(String message) {
        super(message);
    }
}
