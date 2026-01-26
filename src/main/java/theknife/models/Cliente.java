package theknife.models;

import theknife.enums.Enums;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * Classe Cliente, rappresenta l'oggetto Cliente ereditando {@link Utente}.
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class Cliente extends Utente {

    /**
     * Costruttore vuoto
     */
    public Cliente() {
    }

    /**
     * Costruttore facilitato.
     * Utilizzato principalmente per importazione dati da csv.
     *
     * @param id            Id
     * @param nome          Nome
     * @param cognome       Cognome
     * @param username      Username
     * @param password      Password
     * @param dataDiNascita Data di Nascita
     * @param nazione       Nazione
     * @param citta         Città
     * @param indirizzo     Indirizzo
     * @param ristoranti    Lista dei ristoranti preferiti
     */
    public Cliente(int id, String nome, String cognome, String username, String password, Date dataDiNascita,
            String nazione, String citta, String indirizzo, ArrayList<Integer> ristoranti) {
        super(id, nome, cognome, username, password, dataDiNascita, nazione, citta, indirizzo, Enums.Ruolo.CLIENTE,
                ristoranti);
    }
}
