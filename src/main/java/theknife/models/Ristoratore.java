package theknife.models;

import theknife.enums.Enums.Ruolo;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * Classe Ristoratore, rappresenta l'oggetto Ristoratore che eredita da
 * {@link Utente}.
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class Ristoratore extends Utente {

    /**
     * Costruttore vuoto
     */
    public Ristoratore() {
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
     * @param ristoranti    Lista dei ristoranti gestiti
     */
    public Ristoratore(int id, String nome, String cognome, String username, String password, Date dataDiNascita,
                       String nazione, String citta, String indirizzo, ArrayList<Integer> ristoranti) {
        super(id, nome, cognome, username, password, dataDiNascita, nazione, citta, indirizzo, Ruolo.RISTORATORE,
                ristoranti);
    }
}
