package theknife.businessLogic;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import theknife.models.Cliente;
import theknife.models.Ristorante;
import theknife.utility.UtentiManager;

public class BLCliente {

  private final Cliente cliente;

  public BLCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  /**
   * Aggiunge un ristorante ai preferiti del cliente.
   *
   * @throws FileNotFoundException
   */
  public void aggiungiPreferito(int ristoranteID) throws FileNotFoundException {
    if (!cliente.getRistorantiIDs().contains(ristoranteID)) {

      cliente.getRistorantiIDs().add(ristoranteID);

      aggiornaUtente();
    }
  }

  /**
   * Rimuove un ristorante dai preferiti del cliente.
   *
   * @throws FileNotFoundException
   */
  public void rimuoviPreferito(int ristoranteID) throws FileNotFoundException {
    cliente.getRistorantiIDs().remove(Integer.valueOf(ristoranteID));

    aggiornaUtente();
  }

  /**
   * Aggiorna i dati dell'utente nel file.
   *
   * @throws FileNotFoundException
   */
  private void aggiornaUtente() throws FileNotFoundException {
    UtentiManager.scriviUtenti(new ArrayList<>(UtentiManager.getUtenti().values()));
  }

}
