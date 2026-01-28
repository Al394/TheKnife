package theknife.navigation;

import theknife.enums.Enums;
import theknife.utility.TheKnifeLogger;
import theknife.utility.UtentiManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class RegistrazionePage extends Navigation {

    private static final String DATE_PATTERN = "MM/dd/yyyy";

    public RegistrazionePage(Scanner scanner) {
        super(scanner);
    }

    /**
     * Avvia la pagina di registrazione.
     */
    public void start() {
        String nome = "";
        String cognome = "";
        String username = "";
        String password = "";
        String dataDiNascitaStr = "";
        Date dataDiNascita = null;
        String nazione = "";
        String citta = "";
        String indirizzo = "";
        String ruolo = "";
        Enums.Ruolo ruoloEnum = null;

        while (true) {

            stampaMenu();

            nome = loopLeggiInput("Inserisci Nome: ");

            cognome = loopLeggiInput("Inserisci Cognome: ");

            username = loopLeggiInput("Inserisci Username: ");

            while (password.isBlank()) {
                password = leggiInput("Inserisci Password: ");
                if (!isPasswordValida(password)) {
                    scriviErrore("La password deve essere di almeno 4 caratteri.");

                    // Evito di uscire dal ciclo se la password non è valida.
                    password = "";
                }
            }

            while (dataDiNascitaStr.isBlank()) {
                dataDiNascitaStr = leggiInput("Inserisci Data di Nascita [Giorno/Mese/Anno -> dd/MM/yyyy]: ");

                try {
                    dataDiNascita = parseDate(dataDiNascitaStr);

                } catch (Exception e) {
                    scriviErrore("La data di nascita non è valida.");
                    TheKnifeLogger.error(e);

                    // Evito di uscire dal ciclo se la data non è valida.
                    dataDiNascitaStr = "";
                }
            }

            nazione = loopLeggiInput("Inserisci Nazione: ");

            citta = loopLeggiInput("Inserisci Città: ");

            indirizzo = loopLeggiInput("Inserisci Indirizzo: ");

            while (ruolo.isBlank()) {
                ruolo = loopLeggiInput("Inserisci il tuo ruolo:\n1 | Cliente\n2 | Ristoratore\n");

                ruoloEnum = ruolo.equals("1") ? Enums.Ruolo.CLIENTE
                        : ruolo.equals("2") ? Enums.Ruolo.RISTORATORE : null;

                if (ruoloEnum == null) {
                    scriviErrore("Ruolo non valido.\n1 | Cliente\n2 | Ristoratore.");

                    ruolo = "";
                }
            }

            try {
                UtentiManager.registraUtente(nome, cognome, username, password, dataDiNascita, nazione, citta,
                        indirizzo,
                        ruoloEnum);

                pulisciConsole();

                scriviMessaggio("Utente registrato con successo!");

                return;
            } catch (Exception e) {
                TheKnifeLogger.error(e);

                scriviMessaggio("Errore durante la registrazione dell'utente. " + e.getMessage());
            }
        }
    }

    private boolean isPasswordValida(String password) {
        return password != null && password.length() >= 4;
    }

    private Date parseDate(String date) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);

        // Valido il formato della data, altrimenti anche questo input è valido:
        // 77/77/7777
        sdf.setLenient(false);

        return sdf.parse(date);
    }

    private void stampaMenu() {
        pulisciConsole();

        scriviMessaggio("==============================");
        scriviMessaggio("      NUOVA REGISTRAZIONE     ");
        scriviMessaggio("==============================");
        scriviMessaggio("0 | Torna indietro.");
    }

}
