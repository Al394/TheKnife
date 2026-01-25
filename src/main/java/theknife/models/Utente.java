package theknife.models;

import java.util.Date;
import java.util.List;

import theknife.enums.Enums.Ruolo;

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
    protected String Nazione;

    /**
     * Città utente.
     */
    protected String Citta;

    /**
     * Indirizzo utente.
     */
    protected String Indirizzo;
    /**
     * Ruolo utente.
     */
    protected Ruolo ruolo;

    /**
     * Lista ristoranti legati all'utente.
     * Preferiti nel caso Cliente, gestiti nel caso Proprietario.
     */
    protected List<Integer> ristorantiIDs;

    /** Getters **/
    public int getId() {
        return id;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public String getNazione() {
        return Nazione;
    }

    public String getCitta() {
        return Citta;
    }

    public String getIndirizzo() {
        return Indirizzo;
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

    public List<Integer> getRistorantiIDs() {
        return ristorantiIDs;
    }

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
            String nazione, String citta, String indirizzo, Ruolo role, List<Integer> ristoranti) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
        this.dataDiNascita = dataDiNascita;
        this.Nazione = nazione;
        this.Citta = citta;
        this.Indirizzo = indirizzo;
        this.ruolo = role;
        this.ristorantiIDs = ristoranti;
    }

}
