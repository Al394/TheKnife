package theknife.businessLogic;

import theknife.models.Ristorante;

import java.util.ArrayList;
import java.util.List;

public class BLRistoranti {

    private final List<Ristorante> ristoranti;

    /**
     * Il service riceve i dati già caricati dal repository/manager.
     */
    public BLRistoranti(List<Ristorante> ristoranti) {
        this.ristoranti = ristoranti;
    }
    public List<Ristorante> cerca(
            double latUtente,
            double lonUtente,
            double raggioKm,
            String citta,
            String tipoCucina,
            double prezzoMassimo,
            boolean delivery,
            boolean prenotazioneOnline
    ) {

        List<Ristorante> risultati = new ArrayList<>();

        for (Ristorante r : ristoranti) {

            double distanza = 0;
//                    GeoUtils.distanzaKm(
//                    latUtente, lonUtente,
//                    r.getLatitudine(), r.getLongitudine()
//            );

            if (distanza > raggioKm) {
                continue;
            }

            if (citta != null && !r.getCitta().equalsIgnoreCase(citta)) {
                continue;
            }

            if (tipoCucina != null && !r.getTipoCucina().equalsIgnoreCase(tipoCucina)) {
                continue;
            }

            if (prezzoMassimo > 0 && r.getPrezzoMedio() > prezzoMassimo) {
                continue;
            }

            if (delivery && !r.isTakeAway()) {
                continue;
            }

            if (prenotazioneOnline && !r.hasBooking()) {
                continue;
            }

            risultati.add(r);
        }

        return risultati;
    }
}
