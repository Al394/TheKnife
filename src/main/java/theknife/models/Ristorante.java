package theknife.models;

import java.util.ArrayList;
import theknife.exceptions.ValidationException;

/**
 *
 * Classe che rappresenta l'oggetto Ristorante.
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class Ristorante {

    /**
     * ID ristorante.
     */
    private int id;

    /**
     * Nome ristorante
     */
    private String nome;
    /**
     * Nazione ristorante
     */
    private String nazione;
    /**
     * Città ristorante
     */
    private String citta;
    /**
     * Indirizzo ristorante
     */
    private String indirizzo;

    /**
     * Latitudine e longitudine ristorante.
     */
    private Coordinate locazione;
    /**
     * Prezzo medio menu ristorante nel formato "€€", "€€€", ecc.
     */
    private String prezzoMedio;
    /**
     * Presente il servizio di delivery.
     */
    private boolean delivery;
    /**
     * Presente il servizio di prenotazione online.
     */
    private boolean booking;
    /**
     * Tipo di cucine del ristorante.
     */
    private String tipoCucina;
    /**
     * Descrizione del ristorante.
     */
    private String descrizione;
    /**
     * Servizi fortini dal ristorante.
     */
    private String servizi;

    /**
     * Lista recensioni del Ristorante.
     */
    private ArrayList<Integer> recensioniIDs;

    /** Getters **/

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getNazione() {
        return nazione;
    }

    public String getCitta() {
        return citta;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getPrezzoMedio() {
        return prezzoMedio;
    }

    public double getLatitudine() {
        return locazione.getLatitudine();
    }

    public double getLongitudine() {
        return locazione.getLongitudine();
    }

    public boolean isTakeAway() {
        return delivery;
    }

    public boolean hasBooking() {
        return booking;
    }

    public String getTipoCucina() {
        return tipoCucina;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getServizi() {
        return servizi;
    }

    public ArrayList<Integer> getRecensioniIDs() {
        return recensioniIDs;
    }

    /**
     * Costruttore vuoto.
     */
    public Ristorante() {
        this.recensioniIDs = new ArrayList<>();
    }

    /**
     * Costruttore completo per la classe Ristorante.
     *
     * @param id          ID ristorante
     * @param nome        Nome ristorante
     * @param nazione     Nazione
     * @param citta       Città
     * @param indirizzo   Indirizzo
     * @param latitudine  Latitudine
     * @param longitudine Longitudine
     * @param prezzoMedio Prezzo medio
     * @param delivery    Effettua consegne a domicilio
     * @param booking     Servizio prenotazione online disponibile
     * @param tipoCucina  Tipologia di cucina (es: Italiana, Sushi, ecc.)
     * @throws ValidationException Coordinate non valide.
     */
    public Ristorante(int id, String nome, String nazione, String citta, String indirizzo,
            double latitudine, double longitudine, String prezzoMedio,
            boolean delivery, boolean booking, String tipoCucina, String descrizione, String servizi,
            ArrayList<Integer> recensioniIDs)
            throws ValidationException {
        this.id = id;
        this.nome = nome;
        this.nazione = nazione;
        this.citta = citta;
        this.indirizzo = indirizzo;
        this.locazione = new Coordinate(latitudine, longitudine);
        this.prezzoMedio = prezzoMedio;
        this.delivery = delivery;
        this.booking = booking;
        this.tipoCucina = tipoCucina;
        this.descrizione = descrizione;
        this.servizi = servizi;
        this.recensioniIDs = recensioniIDs;
    }

    /**
     * Aggiunge una nuova recensione.
     *
     * @param recensione recensione da aggiungere
     */
    public void aggiungiRecensione(int recensione) {
        recensioniIDs.add(recensione);
    }

    /**
     * Restituisce la media delle stelle.
     *
     * @return media stelle
     */
    public double getMediaStelle() {
        double result = 0;
        int sum = 0;

        if (!recensioniIDs.isEmpty()) {
            /* Ciclo la lista recensioni. */
            for (int idRecensione : recensioniIDs) {
                sum += idRecensione;
            }

            /* Faccio la media */
            result = (double) sum / recensioniIDs.size();
        }

        return result;
    }

    public int getNumeroPrezzoMedio() {
        return prezzoMedio.length();
    }

    @Override
    public String toString() {
        return nome + " (" + citta + ") - " + tipoCucina +
                " | Prezzo medio: " + prezzoMedio +
                " | Media stelle: " + String.format("%.2f", getMediaStelle());
    }

}
