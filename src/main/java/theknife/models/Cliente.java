package theknife.models;

import theknife.utility.Enums;

import java.util.Date;
import java.util.List;

/**
 *
 * Classe Cliente, rappresenta l'oggetto Cliente ereditando Utente.
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
     * @param domicilio     Domicilio
     * @param ristoranti    Lista dei ristoranti preferiti
     */
    public Cliente(int id, String nome, String cognome, String username, String password, Date dataDiNascita,
            String domicilio, List<Integer> ristoranti) {
        super(id, nome, cognome, username, password, dataDiNascita, domicilio, Enums.Ruolo.CLIENTE, ristoranti);
    }
}
