package theknife.models;

import theknife.utility.Enums;

import java.util.Date;

public class Admin extends Utente {

    /**
     * Costruttore vuoto
     */
    public Admin() {
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
    public Admin(String nome, String cognome, String username, String password, Date dataDiNascita, String domicilio) {
        super(nome, cognome,username,password,dataDiNascita, domicilio, Enums.Ruolo.ADMIN);
    }
}
