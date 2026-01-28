package theknife.businessLogic;

import theknife.models.Ristoratore;

/**
 * Classe di logica.
 * Gestisce la manipolazione del'oggetto {@link theknife.models.Ristorante}.
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class BLRistoratore {
    private final Ristoratore ristoratore;

    public BLRistoratore(Ristoratore ristoratore) {
        this.ristoratore = ristoratore;
    }

    public void aggiungiRiferimentoRistorante(int ristoranteID) {
        ristoratore.getRistorantiIDs().add(ristoranteID);
    }

}
