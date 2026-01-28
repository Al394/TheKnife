package theknife.businessLogic;

import theknife.exceptions.ValidationException;
import theknife.models.FiltroRicerca;
import theknife.models.FiltroRicerca.FiltroPrezzo;
import theknife.models.Recensione;
import theknife.models.Ristorante;
import theknife.utility.RecensioniManager;
import theknife.utility.RitstorantiManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe di logica.
 * Gestisce la manipolazione del'oggetto {@link Ristorante}.
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class BLRistorante {

    /**
     * Riferimento interno ristoranti.
     */
    private final ArrayList<Ristorante> ristorantiTrovati = new ArrayList<>();

    /**
     * Il service riceve i dati già caricati dal repository/manager.
     */
    public BLRistorante() {
    }

    /**
     * Recupera i ristoranti di uno specifico ristoratore.
     *
     * @param ristoratoreIDs Lista degli ID ristoranti del ristoratore
     * @return Lista di ristoranti gestiti dal ristoratore
     */
    public ArrayList<Ristorante> getRistorantiRistoratore(ArrayList<Integer> ristoratoreIDs) {
        ArrayList<Ristorante> ristoranti = new ArrayList<>();
        for (Integer id : ristoratoreIDs) {
            if (RitstorantiManager.getRistoranti().containsKey(id)) {
                ristoranti.add(RitstorantiManager.getRistoranti().get(id));
            }
        }
        return ristoranti;
    }

    /**
     * Recupera il riepilogo delle recensioni per un ristorante.
     *
     * @param ristoranteID ID del ristorante
     * @return Array con media stelle e numero recensioni
     * [mediaStelle, numeroRecensioni]
     * @throws IllegalArgumentException se il ristorante non esiste
     */
    public double[] getRiepilogoRecensioni(int ristoranteID) {
        if (!RitstorantiManager.getRistoranti().containsKey(ristoranteID)) {
            throw new IllegalArgumentException("Ristorante con ID " + ristoranteID + " non trovato");
        }

        Ristorante ristorante = RitstorantiManager.getRistoranti().get(ristoranteID);

        double[] riepilogo = new double[2];

        riepilogo[0] = ristorante.getMediaStelle();
        riepilogo[1] = ristorante.getRecensioniIDs().size();

        return riepilogo;
    }

    /**
     * Recupera tutte le recensioni di un ristorante.
     *
     * @param ristoranteID ID del ristorante
     * @return Lista di recensioni del ristorante
     * @throws IllegalArgumentException se il ristorante non esiste
     */
    public ArrayList<Recensione> getRecensioniRistorante(int ristoranteID) {
        if (!RitstorantiManager.getRistoranti().containsKey(ristoranteID)) {
            throw new IllegalArgumentException("Ristorante con ID " + ristoranteID + " non trovato");
        }

        return RitstorantiManager.getRistoranti().get(ristoranteID).getRecensioni();
    }

    /**
     * Aggiunge una risposta a una recensione.
     *
     * @param ristoranteScelto ID del ristoranteScelto
     * @param recensioneScelta ID della recensione
     * @param risposta         Testo della risposta
     * @throws IOException
     * @throws IllegalArgumentException se ristoranteScelto o recensione non esistono
     */
    public void rispondiRecensione(Ristorante ristoranteScelto, Recensione recensioneScelta, String risposta)
            throws IOException {
        ristoranteScelto.getRecensioni().remove(recensioneScelta);

        recensioneScelta.setRisposta(risposta);

        ristoranteScelto.getRecensioni().add(recensioneScelta);

        // Aggiorno file ristoranti.
        RitstorantiManager.scriviRistoranti(RitstorantiManager.getRistoranti().values().stream().toList());

        RecensioniManager rManager = RecensioniManager.getInstance();

        // Sovrascrivo la recensione aggiornandola.
        rManager.getRecensioni().put(recensioneScelta.getId(), recensioneScelta);

        rManager.scriviRecensioni();
    }

    /**
     * Ricerca ristoranti in base ai filtri specificati.
     * La locazione geografica (città) è obbligatoria.
     *
     * @param filtroRicerca Filtri di ricerca (città obbligatoria)
     * @return Lista di ristoranti che soddisfano i criteri
     * @throws IllegalArgumentException se la città non è specificata
     */
    public ArrayList<Ristorante> cercaRistorante(FiltroRicerca filtroRicerca) {
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
            if (filtroRicerca.hasDelivery() && !r.isTakeAway()) {
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
        return ristorantiTrovati;
    }

    /**
     * Verifica se un ristorante rientra nella fascia di prezzo specificata.
     *
     * @param ristorante   Ristorante da verificare
     * @param filtroPrezzo Filtro prezzo con operazione e valori
     * @return true se il ristorante rientra nella fascia, false altrimenti
     */
    private boolean verificaFasciaPrezzo(Ristorante ristorante, FiltroPrezzo filtroPrezzo) {
        double prezzoMedio = ristorante.getPrezzoMedio();
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

    public Ristorante aggiungiRistorante(String nome, String nazione, String citta, String indirizzo,
                                         double latitudine, double longitudine, int prezzoMedio,
                                         boolean delivery, boolean booking, String tipoCucina, String descrizione, String servizi)
            throws ValidationException, IOException {

        return RitstorantiManager.aggiungRistorante(nome, nazione, citta, indirizzo, latitudine,
                longitudine, prezzoMedio, delivery, booking, tipoCucina, descrizione, servizi);
    }

}
