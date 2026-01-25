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
    stampaMessaggio("=== NUOVA REGISTRAZIONE ===");

    String nome = leggiInput("Inserisci Nome: ");
    if (nome.isBlank()) {
      stampaMessaggio("Errore: Il nome non può essere vuoto.");
      return;
    }

    String cognome = leggiInput("Inserisci Cognome: ");
    if (cognome.isBlank()) {
      stampaMessaggio("Errore: Il cognome non può essere vuoto.");
      return;
    }

    String username = leggiInput("Inserisci Username: ");
    // Validazione base nel controller
    if (username.isBlank()) {
      stampaMessaggio("Errore: Lo username non può essere vuoto.");
      return;
    }

    String password = leggiInput("Inserisci Password: ");
    if (!isPasswordValida(password)) {
      stampaMessaggio("Errore: La password deve essere di almeno 4 caratteri.");
    }

    String dataDiNascita = leggiInput("Inserisci Data di Nascita (dd-MM-yyyy): ");
    if (dataDiNascita == null) {
      stampaMessaggio("Errore: La data di nascita non è valida.");
      return;
    }

    String domicilio = leggiInput("Inserisci Domicilio: ");
    if (domicilio.isBlank()) {
      stampaMessaggio("Errore: Il domicilio non può essere vuoto.");
      return;
    }

    String ruolo = leggiInput("Inserisci il tuo ruolo: 1. Cliente 2. Ristoratore\n");
    if (ruolo.isBlank()) {
      stampaMessaggio("Errore: Il ruolo non può essere lasciato vuoto.");
      return;
    }

    try {
      Enums.Ruolo ruoloEnum = ruolo.equals("1") ? Enums.Ruolo.CLIENTE
          : ruolo.equals("2") ? Enums.Ruolo.RISTORATORE : null;
      UtentiManager.registraUtente(nome, cognome, username, password, dataDiNascita, domicilio, ruoloEnum);
    } catch (Exception e) {
      TheKnifeLogger.error(e);

      stampaMessaggio("Errore durante la registrazione dell'utente. " + e.getMessage());
    }

    stampaMessaggio("Utente registrato con successo!");
  }

  private boolean isPasswordValida(String password) {
    return password != null && password.length() >= 4;
  }

}
