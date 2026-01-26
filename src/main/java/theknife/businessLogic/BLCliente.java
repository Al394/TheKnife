package theknife.businessLogic;

import theknife.models.Cliente;
import theknife.utility.UtentiManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
        aggiungiRiferimentoRistorante(ristoranteID);
    }

    /**
     * Aggiungo un preferito per il cliente o un ristorante gestito per un
     * ristoratore.
     *
     * @param ristoranteID
     * @throws FileNotFoundException
     */
    private void aggiungiRiferimentoRistorante(int ristoranteID) throws FileNotFoundException {
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
        cliente.getRistorantiIDs().remove(cliente.getRistorantiIDs().indexOf(ristoranteID));

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
