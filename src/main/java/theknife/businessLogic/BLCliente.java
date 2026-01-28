package theknife.businessLogic;

import theknife.models.Cliente;
import theknife.utility.UtentiManager;

import java.io.IOException;
import java.util.ArrayList;

public class BLCliente {

    private final Cliente cliente;

    public BLCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Aggiunge un ristorante ai preferiti del cliente.
     *
     * @throws IOException
     */
    public void aggiungiPreferito(int ristoranteID) throws IOException {
        aggiungiRiferimentoRistorante(ristoranteID);
    }

    /**
     * Aggiungo un preferito per il cliente o un ristorante gestito per un
     * ristoratore.
     *
     * @param ristoranteID
     * @throws IOException
     */
    private void aggiungiRiferimentoRistorante(int ristoranteID) throws IOException {
        if (!cliente.getRistorantiIDs().contains(ristoranteID)) {
            cliente.getRistorantiIDs().add(ristoranteID);

            aggiornaUtente();
        }
    }

    /**
     * Rimuove un ristorante dai preferiti del cliente.
     *
     * @throws IOException
     */
    public void rimuoviPreferito(int ristoranteID) throws IOException {
        cliente.getRistorantiIDs().remove(cliente.getRistorantiIDs().indexOf(ristoranteID));

        aggiornaUtente();
    }

    /**
     * Aggiorna i dati dell'utente nel file.
     *
     * @throws IOException
     */
    private void aggiornaUtente() throws IOException {
        UtentiManager.scriviUtenti(new ArrayList<>(UtentiManager.getUtenti().values()));
    }

}
