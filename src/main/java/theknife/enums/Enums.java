package theknife.enums;

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
     * Enum Operazioni per fasce di prezzo.
     */
    public enum FasceDiPrezzoOp {
        LESS_THAN("LESS"),
        GREATER_THAN("GREATER"),
        BETWEEN("BETWEEN");

        private final String operazione;

        FasceDiPrezzoOp(String operazione) {
            this.operazione = operazione;
        }

        public String getOperazione() {
            return operazione;
        }
    }

    public enum TernaryInfo {
        YES("Yes"),
        NO("No"),
        ANY("Any");

        private final String info;

        TernaryInfo(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }
    }
}
