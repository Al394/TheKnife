package theknife.utility;

/**
 *
 * Classe Enums, rappresenta degli oggetti enum utili per il mantenimento del
 * codice.
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class Enums {
    /**
     * Enum Ruoli.
     */
    public enum Ruolo {
        ADMIN("Admin"),
        CLIENTE("Cliente"),
        RISTORATORE("Ristoratore");

        private final String ruolo;

        Ruolo(String ruolo) {
            this.ruolo = ruolo;
        }

        public String getRuolo() {
            return ruolo;
        }
    }

    /**
     * Tipo file per leggere i dati corretti.
     */
    public enum TipoFile {
        RISTORANTI,
        UTENTI
    }
}
