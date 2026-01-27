package theknife.businessLogic;

import theknife.models.Ristoratore;

public class BLRistoratore {
  private final Ristoratore ristoratore;

  public BLRistoratore(Ristoratore ristoratore) {
    this.ristoratore = ristoratore;
  }

  public void aggiungiRiferimentoRistorante(int ristoranteID) {
    ristoratore.getRistorantiIDs().add(ristoranteID);
  }

}
