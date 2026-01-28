package theknife.models;

import theknife.enums.Enums.Ruolo;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * Classe astratta Utente, rappresenta l'oggetto Utente
 *
 * @author Alessio Sangiorgi 730420 VA
 */
abstract public class Utente {
    /**
     * ID utente.
     */
    protected int id;
    /**
     * Nome utente.
     */
    protected String nome;
    /**
     * Cognome utente.
     */
    protected String cognome;
    /**
     * Username utente.
     */
    protected String username;
    /**
     * Password utente.
     */
    protected String password;
    /**
     * Data di nascita.
     */
    protected Date dataDiNascita;

    /**
     * Nazione utente.
     */
    protected String nazione;

    /**
     * Città utente.
     */
    protected String citta;

    /**
     * Indirizzo utente.
     */
    protected String indirizzo;
    /**
     * Ruolo utente.
     */
    protected Ruolo ruolo;

    /**
     * Lista ristoranti legati all'utente.
     * Preferiti nel caso Cliente, gestiti nel caso Proprietario.
     */
    protected ArrayList<Integer> ristorantiIDs;

    /**
     * Costruttore vuoto.
     */
    public Utente() {
    }

    /**
     *
     * Costruttore facilitato.
     *
     * @param id            Id
     * @param nome          Nome
     * @param cognome       Cognome
     * @param username      Username
     * @param password      Password
     * @param dataDiNascita Data di Nascita
     * @param domicilio     Domicilio
     * @param role          Ruolo
     * @param ristoranti    Lista ristoranti collegati
     */
    public Utente(int id, String nome, String cognome, String username, String password, Date dataDiNascita,
            String nazione, String citta, String indirizzo, Ruolo role, ArrayList<Integer> ristoranti) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
        this.dataDiNascita = dataDiNascita;
        this.nazione = nazione;
        this.citta = citta;
        this.indirizzo = indirizzo;
        this.ruolo = role;
        this.ristorantiIDs = ristoranti;
    }

    /**
     * Getters
     **/
    public int getId() {
        return id;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public String getNazione() {
        return nazione;
    }

    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public Date getDataDiNascita() {
        return dataDiNascita;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getCognome() {
        return cognome;
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Integer> getRistorantiIDs() {
        return ristorantiIDs;
    }

}
