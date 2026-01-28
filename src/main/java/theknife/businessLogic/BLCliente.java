package theknife.businessLogic;

import theknife.models.Cliente;
import theknife.utility.UtentiManager;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe di logica.
 * Gestisce la manipolazione del'oggetto {@link Cliente}.
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class BLCliente {

    private final Cliente cliente;
    private final UtentiManager uManager = UtentiManager.getInstance();

    /**
     * Costruttore.
     *
     * @param cliente
     */
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
        uManager.scriviUtenti(new ArrayList<>(uManager.getUtenti().values()));
    }

}
