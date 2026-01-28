package theknife.navigation;

import theknife.enums.Enums;
import theknife.exceptions.AuthException;
import theknife.models.Utente;
import theknife.utility.UtentiManager;

import java.util.Scanner;

/**
 * Classe Controller per la login.
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class LoginPage extends Navigation {

    private final UtentiManager uManager = UtentiManager.getInstance();

    /**
     * Costruttore
     * @param scanner
     */
    public LoginPage(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void start() {
        while (true) {
            stampaMenu();

            String username = leggiInput("Username: ");

            if (username.equals("0")) {
                return; // torna alla HomeController
            }

            String password = leggiInput("Password: ");

            try {
                Utente utente = uManager.login(username, password);

                scriviMessaggio("Login effettuato con successo!");

                indirizzaAllaPaginaSuccessiva(utente, scanner);

                return; // esce dal login dopo successo

            } catch (AuthException e) {
                scriviErrore(e.getMessage());

                attendiInputBack();
            }
        }
    }

    private void indirizzaAllaPaginaSuccessiva(Utente utente, Scanner scanner) {
        if (utente.getRuolo() == Enums.Ruolo.CLIENTE) {
            new ClientePage(scanner, utente).start();
        } else if (utente.getRuolo() == Enums.Ruolo.RISTORATORE) {
            new RistoratorePage(scanner, utente).start();
        }
    }

    private void stampaMenu() {
        pulisciConsole();

        scriviMessaggio("==============================");
        scriviMessaggio("            LOGIN             ");
        scriviMessaggio("==============================");
        scriviMessaggio("0 | Torna alla home.\n");
    }

}
