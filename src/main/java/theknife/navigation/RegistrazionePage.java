package theknife.navigation;

import theknife.enums.Enums;
import theknife.utility.TheKnifeLogger;
import theknife.utility.UtentiManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class RegistrazionePage extends Navigation {

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

            while (nome.isBlank()) {
                nome = leggiInput("Inserisci Nome: ");

                if (nome.equals("0"))
                    return;

                if (nome.isBlank()) {
                    scriviErrore("Il nome non può essere vuoto.");
                }
            }

            while (cognome.isBlank()) {
                cognome = leggiInput("Inserisci Cognome: ");
                if (cognome.isBlank()) {
                    scriviErrore("Il cognome non può essere vuoto.");
                }
            }

            while (username.isBlank()) {
                username = leggiInput("Inserisci Username: ");
                if (username.isBlank()) {
                    scriviErrore("Lo username non può essere vuoto.");
                }
            }

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

            while (nazione.isBlank()) {
                nazione = leggiInput("Inserisci Nazione: ");
                if (nazione.isBlank()) {
                    scriviErrore("La nazione non può essere vuota.");
                }
            }

            while (citta.isBlank()) {
                citta = leggiInput("Inserisci Città: ");
                if (citta.isBlank()) {
                    scriviErrore("La città non può essere vuota.");
                }
            }

            while (indirizzo.isBlank()) {
                indirizzo = leggiInput("Inserisci Indirizzo: ");
                if (indirizzo.isBlank()) {
                    scriviErrore("L'indirizzo non può essere vuoto.");
                }
            }

            while (ruolo.isBlank()) {
                ruolo = leggiInput("Inserisci il tuo ruolo:\n1 | Cliente\n2 | Ristoratore\n");
                if (ruolo.isBlank()) {
                    scriviErrore("Il ruolo non può essere lasciato vuoto.");
                }
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
        return new SimpleDateFormat("dd/MM/yyyy").parse(date);
    }

    private void stampaMenu() {
        pulisciConsole();

        scriviMessaggio("==============================");
        scriviMessaggio("      NUOVA REGISTRAZIONE     ");
        scriviMessaggio("==============================");
        System.out.println("0 | Torna indietro.");
    }

}
