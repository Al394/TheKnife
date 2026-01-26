package theknife.utility;

import theknife.models.Coordinate;

/**
 * Classe GeoLocationManager, fornisce metodi statici per la gestione
 * di operazioni geografiche come il calcolo della distanza tra due punti
 * sulla superficie terrestre utilizzando la formula di Haversine.
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class GeoLocationManager {
    /**
     * Raggio terrestre in KM.
     * Rappresentato come media tra raggio equatoriale e polare.
     * Costante per il calcolo della formula di Haversine.
     */
    private static final int RAGGIO_TERRESTRE_KM = 6371;

    /**
     * Costruttore privato per rendere la classe "statica".
     */
    private GeoLocationManager() {
    }

    /**
     * Calcolo la distanza tra due punti sulla Terra utilizzando la formula di
     * Haversine.
     *
     * @param coordinata1 Prima coordinata.
     * @param coordinata2 Seconda coordinata.
     * @return Distanza tra due punti in Km.
     */
    public static double calcoloDistanza(Coordinate coordinata1, Coordinate coordinata2) {
        double lon1 = coordinata1.getLongitudine();
        double lon2 = coordinata2.getLongitudine();
        double lat1 = coordinata1.getLatitudine();
        double lat1_R = coordinata1.getLatitudineInRadianti();
        double lat2 = coordinata2.getLatitudine();
        double lat2_R = coordinata2.getLatitudineInRadianti();

        // Ottengo il delta in radianti
        double latDelta_R = Math.toRadians(lat2 - lat1);
        double longDelta_R = Math.toRadians(lon2 - lon1);

        // Tutti gli input devono essere in radianti.
        double hav = formulaDiHaversine(latDelta_R, lat1_R, lat2_R, longDelta_R);

        // Calcolo della distanza angolare.
        double c = 2 * Math.atan2(Math.sqrt(hav), Math.sqrt(1 - hav));

        // Distanza finale in Km.
        return RAGGIO_TERRESTRE_KM * c;
    }

    /**
     * Applico la formula di Haversine per calcolare il valore intermedio
     * {@code hav}
     * utilizzato nel calcolo della distanza ortodromica tra due punti sulla
     * superficie terrestre.
     *
     * <p>
     * Il valore restituito rappresenta il quadrato della metà della corda
     * che unisce i due punti sulla sfera.
     * </p>
     *
     * <p>
     * <strong>Nota:</strong> Tutti i parametri devono essere espressi in radianti.
     * </p>
     *
     * @param latDelta  differenza di latitudine tra i due punti, in radianti.
     * @param lat1      latitudine del primo punto, in radianti.
     * @param lat2      latitudine del secondo punto, in radianti.
     * @param longDelta differenza di longitudine tra i due punti, in radianti.
     * @return il valore {@code a} della formula di Haversine.
     * @see <a href="https://en.wikipedia.org/wiki/Haversine_formula">Haversine
     * formula.</a>
     */
    private static double formulaDiHaversine(double latDelta,
                                             double lat1,
                                             double lat2,
                                             double longDelta) {
        return Math.pow(Math.sin(latDelta / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(longDelta / 2), 2);
    }
}
