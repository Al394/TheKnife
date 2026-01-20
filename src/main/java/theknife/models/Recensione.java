package theknife.models;

import theknife.exceptions.ValidationException;

/**
 *
 * Classe Recensione, rappresenta l'oggetto Recensione
 * @author Alessio Sangiorgi 730420 VA
 */
public class Recensione {

    /**
     * Valutazione in stelle da 1 a 5
     */
    private byte stelle;

    /**
     * Testo inserito a commento della recensione.
     */
    private String commento;

    /**
     * Riferimento al Cliente che ha effettuato la recensione.
     */
    private Cliente autore;

    /**
     * Riferimento al ristorante recensito.
     */
    private Ristorante ristorante;

    /**
     * Risposta opzionale del ristoratore.
     */
    private String risposta;

    /**
     * Costruttore.
     * @param stelle Valutazione stelle. Range [1-5]
     * @param commento Recensione cliente.
     * @param autore Utente che crea la recensione.
     * @param ristorante Ristorante riferito alla recensione.
     * @throws ValidationException se le stelle non sono nel range 1-5
     */
    public Recensione(byte stelle, String commento, Cliente autore, Ristorante ristorante) throws ValidationException {
        setStelle(stelle);

        this.commento = commento;
        this.autore = autore;
        this.ristorante = ristorante;
        this.risposta = null;
    }

    /**
     * Getter stelle.
     * @return stelle [1-5]
     */
    public byte getStelle() {
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

    /**
     * Setter per risposta recensione.
     * @param risposta Commento da parte del ristoratore.
     */
    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }
}