package theknife.models;

import theknife.exceptions.ValidationException;

/**
 * Rappresenta una coordinata geografica definita da latitudine e longitudine.
 *
 * <p>
 * I valori sono espressi in <strong>gradi decimali</strong> secondo
 * il sistema di riferimento WGS84, comunemente utilizzato nei sistemi GPS.
 * </p>
 *
 * <ul>
 * <li>La latitudine varia da -90.0 (Sud) a 90.0 (Nord)</li>
 * <li>La longitudine varia da -180.0 (Ovest) a 180.0 (Est)</li>
 * </ul>
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class Coordinate {

    /**
     * Latitudine in gradi decimali.
     */
    private double latitudine;

    /**
     * Longitudine in gradi decimali.
     */
    private double longitudine;

    /**
     * Oggetto {@code Coordinate} che rappresenta latitudine e longitudine di un
     * punto terrestre.
     *
     * @param latitudine  latitudine in gradi.
     * @param longitudine longitudine in gradi.
     * @throws ValidationException
     */
    public Coordinate(double latitudine, double longitudine) throws ValidationException {
        validateCoordinate(latitudine, longitudine);

        this.latitudine = latitudine;
        this.longitudine = longitudine;

    }

    /**
     * Latitudine del punto.
     *
     * @return Latitudine.
     */
    public double getLatitudine() {
        return latitudine;
    }

    /**
     * Longitudine del punto.
     *
     * @return Longitudine.
     */
    public double getLongitudine() {
        return longitudine;
    }

    /**
     * Restituisce la latitudine convertita in radianti.
     *
     * @return Latitudine in radianti
     */
    public double getLatitudineInRadianti() {
        return Math.toRadians(latitudine);
    }

    /**
     * Restituisce la longitudine convertita in radianti.
     *
     * @return Longitudine in radianti
     */
    public double getLongitudineInRadianti() {
        return Math.toRadians(longitudine);
    }

    /**
     * Imposta le coordinate geografiche validandone i range.
     *
     * @param longitudine Latitudine
     * @param latitudine  Longitudine
     * @throws ValidationException
     */
    private void validateCoordinate(double latitudine, double longitudine) throws ValidationException {
        if (latitudine < -90 || latitudine > 90 || longitudine < -180 || longitudine > 180) {
            throw new ValidationException("Coordinate geografiche non valide");
        }
    }

}
