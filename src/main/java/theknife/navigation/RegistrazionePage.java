package theknife.navigation;

import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import theknife.enums.Enums;
import theknife.utility.TheKnifeLogger;
import theknife.utility.UtentiManager;

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

    stampaMessaggio("=== NUOVA REGISTRAZIONE ===");

    while (nome.isBlank()) {
      nome = leggiInput("Inserisci Nome: ");
      if (nome.isBlank()) {
        stampaMessaggio("Errore: Il nome non può essere vuoto.");
      }
    }

    while (cognome.isBlank()) {
      cognome = leggiInput("Inserisci Cognome: ");
      if (cognome.isBlank()) {
        stampaMessaggio("Errore: Il cognome non può essere vuoto.");
      }
    }

    while (username.isBlank()) {
      username = leggiInput("Inserisci Username: ");
      if (username.isBlank()) {
        stampaMessaggio("Errore: Lo username non può essere vuoto.");
      }
    }

    while (password.isBlank()) {
      password = leggiInput("Inserisci Password: ");
      if (!isPasswordValida(password)) {
        stampaMessaggio("Errore: La password deve essere di almeno 4 caratteri.");

        // Evito di uscire dal ciclo se la password non è valida.
        password = "";
      }
    }

    while (dataDiNascitaStr.isBlank()) {
      dataDiNascitaStr = leggiInput("Inserisci Data di Nascita (dd-MM-yyyy): ");

      try {
        dataDiNascita = parseDate(dataDiNascitaStr);

      } catch (Exception e) {
        stampaMessaggio("Errore: La data di nascita non è valida.");
        TheKnifeLogger.error(e);

        // Evito di uscire dal ciclo se la data non è valida.
        dataDiNascitaStr = "";
      }
    }

    while (nazione.isBlank()) {
      nazione = leggiInput("Inserisci Nazione: ");
      if (nazione.isBlank()) {
        stampaMessaggio("Errore: La nazione non può essere vuota.");
      }
    }

    while (citta.isBlank()) {
      citta = leggiInput("Inserisci Città: ");
      if (citta.isBlank()) {
        stampaMessaggio("Errore: La città non può essere vuota.");
      }
    }

    while (indirizzo.isBlank()) {
      indirizzo = leggiInput("Inserisci Indirizzo: ");
      if (indirizzo.isBlank()) {
        stampaMessaggio("Errore: L'indirizzo non può essere vuoto.");
      }
    }

    while (ruolo.isBlank()) {
      ruolo = leggiInput("Inserisci il tuo ruolo:\n1. Cliente\n2. Ristoratore\n");
      if (ruolo.isBlank()) {
        stampaMessaggio("Errore: Il ruolo non può essere lasciato vuoto.");
      }
      ruoloEnum = ruolo.equals("1") ? Enums.Ruolo.CLIENTE
          : ruolo.equals("2") ? Enums.Ruolo.RISTORATORE : null;

      if (ruoloEnum == null) {
        stampaMessaggio("Errore: Ruolo non valido. Inserisci 1 per Cliente o 2 per Ristoratore.");

        ruolo = "";
      }
    }

    try {
      UtentiManager.registrazione(nome, cognome, username, password, dataDiNascita, nazione, citta, indirizzo,
          ruoloEnum);
    } catch (Exception e) {
      TheKnifeLogger.error(e);

      stampaMessaggio("Errore durante la registrazione dell'utente. " + e.getMessage());

      return;
    }

    pulisciConsole();

    stampaMessaggio("Utente registrato con successo!");
  }

  private boolean isPasswordValida(String password) {
    return password != null && password.length() >= 4;
  }

  private Date parseDate(String date) throws ParseException {
    return new SimpleDateFormat("dd-MM-yyyy").parse(date);
  }

}
