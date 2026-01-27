package theknife.businessLogic;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import theknife.exceptions.ValidationException;
import theknife.models.Recensione;
import theknife.models.Ristorante;
import theknife.utility.RecensioniManager;
import theknife.utility.RitstorantiManager;
import theknife.utility.TheKnifeLogger;

public class BLRecensione {

    private final RecensioniManager recManager = RecensioniManager.getInstance();
    private Recensione recensione;

    public BLRecensione(Recensione rec) {
        this.recensione = rec;
    }

    public BLRecensione() {
    }

    /**
     * Elimina una recensione dell'utente.
     */
    public boolean eliminaRecensione() {
        HashMap<Integer, Recensione> recensioni = recManager.getRecensioni();
        HashMap<Integer, Ristorante> ristoranti = RitstorantiManager.getRistoranti();

        rimuovoRiferimentoRistoranti(ristoranti);

        Boolean removed = rimuovoRiferimentoRecensioni(recensioni);

        try {
            recManager.scriviRecensioni();
        } catch (Exception e) {
            TheKnifeLogger.error(e);
        }

        return removed;
    }

    /**
     * Metodo per aggiungere una nuova recensione.
     *
     * Valido stelle e unicità per ristorante.
     *
     * @param nuovoID
     * @param stelle
     * @param commento
     * @param autoreID
     * @param ristoranteID
     * @throws ValidationException
     * @throws FileNotFoundException
     */
    public void aggiungiRecensione(int nuovoID, byte stelle, String commento, int autoreID, int ristoranteID)
            throws ValidationException, FileNotFoundException {
        Recensione nuovaRecensione = new Recensione(nuovoID, stelle, commento, autoreID, ristoranteID);

        validaRistoranteUnico(nuovaRecensione);

        recManager.addRecensione(nuovaRecensione);

        try {
            recManager.scriviRecensioni();
        } catch (java.io.FileNotFoundException e) {
            TheKnifeLogger.error(e);

            throw e;
        }
    }

    private void validaRistoranteUnico(Recensione nuovaRecensione) throws ValidationException {
        HashMap<Integer, Ristorante> ristoranti = RitstorantiManager.getRistoranti();

        Ristorante ristorante = ristoranti.get(nuovaRecensione.getRistoranteID());

        ArrayList<Recensione> recensioniRistorante = ristorante.getRecensioni();

        for (Recensione recensione : recensioniRistorante) {
            if (recensione.getAutoreID() == nuovaRecensione.getAutoreID())
                throw new ValidationException("Hai già inserito una recensione per questo ristorante.");
        }
    }

    private boolean rimuovoRiferimentoRecensioni(HashMap<Integer, Recensione> recensioni) {
        // Remove ritorna l'oggetto precedente se rimosso da chiave, altrimenti null.
        return recManager.getRecensioni().remove(recensione.getId()) != null;
    }

    private void rimuovoRiferimentoRistoranti(HashMap<Integer, Ristorante> ristoranti) {
        Ristorante ristorante = ristoranti.get(recensione.getRistoranteID());

        if (ristorante != null) {
            int pos = ristorante.getRecensioniIDs().indexOf(recensione.getId());

            // Rimuovo dall'arry letto da file
            if (pos != -1)
                ristorante.getRecensioniIDs().remove(pos);

            ristorante.getRecensioni().remove(recensione);

            try {
                RitstorantiManager.scriviRistoranti(new ArrayList<>(RitstorantiManager.getRistoranti().values()));
            } catch (Exception e) {
                TheKnifeLogger.error(e);
            }
        }
    }

}
