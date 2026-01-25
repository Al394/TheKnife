package theknife.models;

import theknife.enums.Enums;
import theknife.enums.Enums.FasceDiPrezzoOp;

/**
 * Modello FiltroRicerca.
 *
 * @author Alessio Sangiorgi 730420 VA
 */
/**
 * Classe FiltroRicerca per la gestione dei parametri di ricerca di ristoranti.
 *
 * Questa classe rappresenta un filtro di ricerca avanzato che consente di
 * specificare
 * vari criteri per la ricerca di ristoranti, tra cui localizzazione geografica,
 * tipo
 * di cucina, fascia di prezzo e servizi disponibili.
 *
 * Attributi principali:
 * - nazione: il paese dove effettuare la ricerca
 * - citta: la città specifica
 * - indirizzo: l'indirizzo specifico o parziale
 * - location: le coordinate geografiche (latitudine, longitudine)
 * - cucina: il tipo di cucina desiderato
 * - filtroPrezzo: il filtro per la fascia di prezzo
 * - takeaway: indica se è richiesto il servizio di asporto
 * - booking: indica se è richiesto il servizio di prenotazione
 * - mediaStelle: la valutazione minima in stelle desiderata
 *
 * La classe contiene anche una classe interna FiltroPrezzo che permette di
 * definire criteri di prezzo con operazioni di confronto (maggiore, minore,
 * intervallo).
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class FiltroRicerca {

  /** Nazione */
  private String nazione;

  /** Città */
  private String citta;
  /** Indirizzo */
  private String indirizzo;
  /** Coordinate */
  private Coordinate location;
  /** Tipo di Cucina */
  private String cucina;
  /** Filtro Prezzo */
  private FiltroPrezzo filtroPrezzo;
  /** Takeaway */
  private boolean takeaway;
  /** Booking */
  private boolean booking;
  /** Media Stelle */
  private byte mediaStelle;

  /**
   * Costruttore FiltroRicerca.
   *
   * @param nazione      Nazione
   * @param citta        Città
   * @param indirizzo    Indirizzo
   * @param location     Coordinate
   * @param cucina       Cucina
   * @param filtroPrezzo Filtro prezzo
   * @param takeaway     possibilità take away
   * @param booking      possibilità
   * @param mediaStelle  media stelle
   */
  public FiltroRicerca(String nazione, String citta, String indirizzo, Coordinate location, String cucina,
      FiltroPrezzo filtroPrezzo, boolean takeaway, boolean booking, byte mediaStelle) {
    this.nazione = nazione.trim();
    this.citta = citta.trim();
    this.indirizzo = indirizzo.trim();
    this.location = location;
    this.cucina = cucina.trim();
    this.filtroPrezzo = filtroPrezzo;
    this.takeaway = takeaway;
    this.booking = booking;
    this.mediaStelle = mediaStelle;
  }

  /** Getters */
  public String getNazione() {
    return nazione;
  }

  public String getCitta() {
    return citta;
  }

  public String getIndirizzo() {
    return indirizzo;
  }

  public Coordinate getLocation() {
    return location;
  }

  public String getCucina() {
    return cucina;
  }

  public FiltroPrezzo getFiltroPrezzo() {
    return filtroPrezzo;
  }

  public boolean isTakeaway() {
    return takeaway;
  }

  public boolean isBooking() {
    return booking;
  }

  public byte getMediaStelle() {
    return mediaStelle;
  }

  public class FiltroPrezzo {
    /**
     * Tipo di operazione: >, <, Between.
     */
    private Enums.FasceDiPrezzoOp operazione;
    /**
     * Prezzo in euro da comparare.
     */
    private int prezzo1;
    /**
     * Prezzo in euro da comparare.
     */
    private int prezzo2;

    public Enums.FasceDiPrezzoOp getOperazione() {
      return operazione;
    }

    public int getPrezzo1() {
      return prezzo1;
    }

    public int getPrezzo2() {
      return prezzo2;
    }

    public FiltroPrezzo(FasceDiPrezzoOp operazione, int prezzo1) {
      this.operazione = operazione;
      this.prezzo1 = prezzo1;
    }

    public FiltroPrezzo(FasceDiPrezzoOp operazione, int prezzo1, int prezzo2) {
      this.operazione = operazione;
      this.prezzo1 = prezzo1;
      this.prezzo2 = prezzo2;
    }

  }

}
