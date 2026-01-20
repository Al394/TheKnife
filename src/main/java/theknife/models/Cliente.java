package theknife.models;

import theknife.utility.Enums;

import java.util.Date;

/**
 *
 * Classe Cliente, rappresenta l'oggetto Cliente ereditando Utente.
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
     * @param nome          Nome
     * @param cognome       Cognome
     * @param username      Username
     * @param password      Password
     * @param dataDiNascita Data di Nascita
     * @param domicilio     Domicilio
     */
    public Cliente(String nome, String cognome, String username, String password, Date dataDiNascita, String domicilio) {
        super(nome, cognome,username,password,dataDiNascita, domicilio, Enums.Ruolo.CLIENTE);
    }
}
