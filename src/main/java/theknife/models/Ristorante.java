package theknife.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Classe che rappresenta l'oggetto Ristorante.
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class Ristorante {



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
     * Latitudine ristorante
     */
    private double latitudine;
    /**
     * Longitudine ristorante
     */
    private double longitudine;
    /**
     * Prezzo medio menu ristorante
     */
    private double prezzoMedio;
    /**
     * Presente il servizio di delivery.
     */
    private boolean takeAway;
    /**
     * Presente il servizio di prenotazione online.
     */
    private boolean booking;
    /**
     * Tipo di cucine del ristorante.
     */
    private String tipoCucina;
    /**
     * Lista recensioni del Ristorante.
     */
    private List<Recensione> recensioni;

    /** Getters **/
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

    public double getLatitudine() {
        return latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public boolean isTakeAway() {
        return takeAway;
    }

    public boolean hasBooking() {
        return booking;
    }

    public String getTipoCucina() {
        return tipoCucina;
    }

    /**
     * Costruttore vuoto.
     */
    public Ristorante() {
    }

    /**
     * Costruttore completo per la classe Ristorante.
     *
     * @param nome        Nome ristorante
     * @param nazione     Nazione
     * @param citta       Città
     * @param indirizzo   Indirizzo
     * @param latitudine  Latitudine
     * @param longitudine Longitudine
     * @param prezzoMedio Prezzo medio
     * @param takeAway    Effettua consegne a domicilio
     * @param booking     Servizio prenotazione online disponibile
     * @param tipoCucina  Tipologia di cucina (es: Italiana, Sushi, ecc.)
     */
    public Ristorante(String nome, String nazione, String citta, String indirizzo,
                      double latitudine, double longitudine, double prezzoMedio,
                      boolean takeAway, boolean booking, String tipoCucina) {
        this.nome = nome;
        this.nazione = nazione;
        this.citta = citta;
        this.indirizzo = indirizzo;
        setCoordinate(latitudine, longitudine);
        setPrezzoMedio(prezzoMedio);
        this.takeAway = takeAway;
        this.booking = booking;
        this.tipoCucina = tipoCucina;
    }

    /**
     * Aggiunge una nuova recensione.
     *
     * @param recensione recensione da aggiungere
     */
    public void aggiungiRecensione(Recensione recensione) {
        recensioni.add(recensione);
    }

    /**
     * Restituisce la media delle stelle.
     *
     * @return media stelle
     */
    public double getMediaStelle() {
        double result = 0;
        int sum = 0;

        if (!recensioni.isEmpty()) {
            /* Ciclo la lista recensioni. */
            for (Recensione recensione : recensioni) {
                sum += recensione.getStelle();
            }

            /* Faccio la media */
            result = (double) sum / recensioni.size();
        }

        return result;
    }

    /**
     * Imposta le coordinate geografiche validandone i range.
     */
    public final void setCoordinate(double latitudine, double longitudine) {
        if (latitudine < -90 || latitudine > 90 || longitudine < -180 || longitudine > 180) {
            throw new IllegalArgumentException("Coordinate geografiche non valide");
        }
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    public double getPrezzoMedio() {
        return prezzoMedio;
    }

    public final void setPrezzoMedio(double prezzoMedio) {
        if (prezzoMedio < 0) {
            throw new IllegalArgumentException("Il prezzo medio non può essere negativo");
        }
        this.prezzoMedio = prezzoMedio;
    }

    @Override
    public String toString() {
        return nome + " (" + citta + ") - " + tipoCucina +
                " | Prezzo medio: " + prezzoMedio + "€" +
                " | Media stelle: " + String.format("%.2f", getMediaStelle());
    }

}