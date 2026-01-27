package theknife.models;

import theknife.exceptions.ValidationException;

/**
 *
 * Classe Recensione, rappresenta l'oggetto Recensione
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class Recensione {

    /**
     * Id Recensione;
     */
    private int id;
    /**
     * Valutazione in stelle da 1 a 5
     */
    private int stelle;

    /**
     * Testo inserito a commento della recensione.
     */
    private String commento;

    /**
     * Riferimento al Cliente che ha effettuato la recensione.
     */
    private int autoreID;

    /**
     * Riferimento al ristorante recensito.
     */
    private int ristoranteID;

    /**
     * Risposta opzionale del ristoratore.
     */
    private String risposta = "";

    /**
     * Costruttore.
     *
     * @param stelle     Valutazione stelle. Range [1-5]
     * @param commento   Recensione cliente.
     * @param autore     Utente che crea la recensione.
     * @param ristorante Ristorante riferito alla recensione.
     * @throws ValidationException se le stelle non sono nel range 1-5
     */
    public Recensione(int id, byte stelle, String commento, int autoreID, int ristoranteID)
            throws ValidationException {
        setStelle(stelle);

        this.id = id;
        this.commento = commento;
        this.autoreID = autoreID;
        this.ristoranteID = ristoranteID;
    }

    public Recensione(int id, byte stelle, String commento, int autoreID, int ristoranteID, String risposta)
            throws ValidationException {
        setStelle(stelle);

        this.id = id;
        this.commento = commento;
        this.autoreID = autoreID;
        this.ristoranteID = ristoranteID;
        this.risposta = risposta;
    }

    /**
     * Getters
     **/
    public int getStelle() {
        return stelle;
    }

    /**
     * Setter delle stelle. Verifica che le stelle siano comprese nel range [1-5]
     */
    public final void setStelle(byte stelle) throws ValidationException {
        if (stelle < 1 || stelle > 5) {
            throw new ValidationException("La valutazione deve essere compresa tra 1 e 5 stelle.");
        }

        this.stelle = stelle;
    }

    public int getId() {
        return id;
    }

    public String getCommento() {
        return commento;
    }

    public int getAutoreID() {
        return autoreID;
    }

    public int getRistoranteID() {
        return ristoranteID;
    }

    public String getRisposta() {
        return risposta;
    }

    /**
     * Setter per risposta recensione.
     *
     * @param risposta Commento da parte del ristoratore.
     */
    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }
}
