package theknife.businessLogic;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import theknife.models.Recensione;
import theknife.models.Ristorante;
import theknife.models.Utente;
import theknife.utility.RecensioniManager;
import theknife.utility.RitstorantiManager;
import theknife.utility.TheKnifeLogger;
import theknife.utility.UtentiManager;

public class BLRecensione {

  private final Recensione recensione;
  private final RecensioniManager recManager = RecensioniManager.getInstance();

  public BLRecensione(Recensione rec) {
    this.recensione = rec;
  }

  /**
   * Elimina una recensione dell'utente.
   */
  public boolean eliminaRecensione() {
    HashMap<Integer, Recensione> recensioni = recManager.getRecensioni();
    HashMap<Integer, Ristorante> ristoranti = RitstorantiManager.getRistoranti();

    rimuovoRiferimentoRistoranti(ristoranti);

    return rimuovoRiferimentoRecensioni(recensioni);
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
