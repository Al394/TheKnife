package theknife.models;

import theknife.exceptions.ValidationException;

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
     * Prezzo medio menu ristorante in euro.
     */
    private int prezzoMedio;
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
    private List<Integer> recensioniIDs;

    /**
     * Lista di recensioni.
     */
    private ArrayList<Recensione> recensioni = new ArrayList<>();

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
                      double latitudine, double longitudine, int prezzoMedio,
                      boolean delivery, boolean booking, String tipoCucina, String descrizione, String servizi,
                      List<Integer> recensioniIDs)
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
     * Getters
     **/

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

    public int getPrezzoMedio() {
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

    public List<Integer> getRecensioniIDs() {
        return recensioniIDs;
    }

    public ArrayList<Recensione> getRecensioni() {
        return recensioni;
    }

    public void setRecensioni(ArrayList<Recensione> recensioni) {
        this.recensioni = recensioni;
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
            result = (double) sum / recensioniIDs.size();
        }

        return result;
    }

    @Override
    public String toString() {
        return nome + " (" + citta + ") - " + tipoCucina +
                " | Prezzo medio: " + prezzoMedio + "Euro" +
                " | Media stelle: " + String.format("%.2f", getMediaStelle());
    }

    public String getDettagliCompleti() {
        return "Nome: " + nome + "\n" +
                "Nazione: " + nazione + "\n" +
                "Città: " + citta + "\n" +
                "Indirizzo: " + indirizzo + "\n" +
                "Locazione: (" + locazione.getLatitudine() + ", " + locazione.getLongitudine() + ")\n" +
                "Prezzo medio: " + prezzoMedio + "Euro\n" +
                "Delivery: " + (delivery ? "Sì" : "No") + "\n" +
                "Booking: " + (booking ? "Sì" : "No") + "\n" +
                "Tipo di cucina: " + tipoCucina + "\n" +
                "Descrizione: " + descrizione + "\n" +
                "Servizi: " + servizi + "\n" +
                "Media stelle: " + String.format("%.2f", getMediaStelle()) + "\n" +
                "Numero recensioni: " + recensioniIDs.size();
    }

}
