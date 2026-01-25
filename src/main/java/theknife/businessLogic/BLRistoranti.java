package theknife.businessLogic;

import theknife.models.FiltroRicerca;
import theknife.models.FiltroRicerca.FiltroPrezzo;
import theknife.models.Ristorante;
import theknife.utility.RitstorantiManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Business logic per la Classe Ristorante
 *
 * @author Alessio Sangiorgi 730420
 */
public class BLRistoranti {

    /**
     * Riferimento interno ristoranti.
     */
    private ArrayList<Ristorante> ristorantiTrovati;

    /**
     * Il service riceve i dati già caricati dal repository/manager.
     */
    public BLRistoranti() {
    }

    /**
     * Ricerca ristoranti in base ai filtri specificati.
     * La locazione geografica (città) è obbligatoria.
     *
     * @param filtroRicerca Filtri di ricerca (città obbligatoria)
     * @return Lista di ristoranti che soddisfano i criteri
     * @throws IllegalArgumentException se la città non è specificata
     */
    public void cercaRistorante(FiltroRicerca filtroRicerca) {
        // Validazione: la città è obbligatoria
        if (filtroRicerca.getCitta() == null || filtroRicerca.getCitta().trim().isEmpty()) {
            throw new IllegalArgumentException("La città è un criterio di ricerca obbligatorio");
        }

        // Ottengo i ristoranti ignorando gli id.
        List<Ristorante> ristoranti = new ArrayList<>(RitstorantiManager.getRistoranti().values());

        for (Ristorante r : ristoranti) {
            // 1. Filtro per città (OBBLIGATORIO)
            if (!r.getCitta().equalsIgnoreCase(filtroRicerca.getCitta())) {
                continue;
            }

            // 2. Filtro per indirizzo (opzionale)
            if (filtroRicerca.getIndirizzo() != null && !filtroRicerca.getIndirizzo().isEmpty()) {
                if (!r.getIndirizzo().equalsIgnoreCase(filtroRicerca.getIndirizzo())) {
                    continue;
                }
            }

            // 3. Filtro per tipologia di cucina (opzionale)
            if (filtroRicerca.getCucina() != null && !filtroRicerca.getCucina().isEmpty()) {
                if (!r.getTipoCucina().equalsIgnoreCase(filtroRicerca.getCucina())) {
                    continue;
                }
            }

            // 4. Filtro per fascia di prezzo (opzionale)
            if (filtroRicerca.getFiltroPrezzo() != null) {
                if (!verificaFasciaPrezzo(r, filtroRicerca.getFiltroPrezzo())) {
                    continue;
                }
            }

            // 5. Filtro per delivery (opzionale)
            if (filtroRicerca.isTakeaway() && !r.isTakeAway()) {
                continue;
            }

            // 6. Filtro per prenotazione online (opzionale)
            if (filtroRicerca.isBooking() && !r.hasBooking()) {
                continue;
            }

            // 7. Filtro per media stelle (opzionale - 0 significa nessun filtro)
            if (filtroRicerca.getMediaStelle() > 0) {
                if (r.getMediaStelle() < filtroRicerca.getMediaStelle()) {
                    continue;
                }
            }

            ristorantiTrovati.add(r);
        }
    }

    /**
     * Verifica se un ristorante rientra nella fascia di prezzo specificata.
     *
     * @param ristorante   Ristorante da verificare
     * @param filtroPrezzo Filtro prezzo con operazione e valori
     * @return true se il ristorante rientra nella fascia, false altrimenti
     */
    private boolean verificaFasciaPrezzo(Ristorante ristorante, FiltroPrezzo filtroPrezzo) {
        double prezzoMedio = ristorante.getNumeroPrezzoMedio();
        int prezzo1 = filtroPrezzo.getPrezzo1();
        int prezzo2 = filtroPrezzo.getPrezzo2();

        switch (filtroPrezzo.getOperazione()) {
            case LESS_THAN:
                // Minore di prezzo1
                return prezzo1 > 0 && prezzoMedio < prezzo1;

            case GREATER_THAN:
                // Maggiore di prezzo1
                return prezzo1 > 0 && prezzoMedio > prezzo1;

            case BETWEEN:
                // Tra prezzo1 e prezzo2
                return prezzo1 > 0 && prezzo2 > 0 && prezzo1 < prezzoMedio && prezzo2 > prezzoMedio;

            default:
                return true;
        }
    }
}
