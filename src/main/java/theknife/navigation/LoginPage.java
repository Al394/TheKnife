package theknife.navigation;

import java.util.Scanner;

import theknife.enums.Enums;
import theknife.exceptions.AuthException;
import theknife.models.Utente;
import theknife.utility.UtentiManager;

public class LoginPage extends Navigation {

  public LoginPage(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void start() {
    stampaMenu();

    while (true) {

      scriviMessaggio("0 | Torna alla home.\n");

      String username = leggiInput("Username: ");

      if (username.equals("0")) {
        return; // torna alla HomeController
      }

      String password = leggiInput("Password: ");

      try {
        Utente utente = UtentiManager.login(username, password);

        scriviMessaggio("Login effettuato con successo!");

        indirizzaAllaPaginaSuccessiva(utente, scanner);

        return; // esce dal login dopo successo

      } catch (AuthException e) {
        scriviErrore(e.getMessage());
      }
    }
  }

  private void indirizzaAllaPaginaSuccessiva(Utente utente, Scanner scanner) {
    if (utente.getRuolo() == Enums.Ruolo.CLIENTE) {
      new ClientePage(scanner).start();
    } else if (utente.getRuolo() == Enums.Ruolo.RISTORATORE) {
      new RistoratorePage(scanner).start();
    }
    throw new UnsupportedOperationException("Unimplemented method 'indirizzaAllaPaginaSuccessiva'");
  }

  private void stampaMenu() {
    pulisciConsole();

    scriviMessaggio("==============================");
    scriviMessaggio("            LOGIN             ");
    scriviMessaggio("==============================");
  }

}
