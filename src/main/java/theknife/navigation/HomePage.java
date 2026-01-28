package theknife.navigation;

import java.util.Scanner;

public class HomePage extends Navigation {

    public HomePage(Scanner scanner) {
        super(scanner);
    }

    /**
     * Avvia la home page e gestisce il menu principale.
     */
    public void start() {

        boolean exit = false;

        while (!exit) {
            stampaMenu();

            String scelta = leggiInput("Seleziona un'opzione: ");

            switch (scelta) {
                case "1":
                    vaiAllaLogin();
                    break;

                case "2":
                    continuaComeGuest();
                    break;

                case "3":
                    vaiAllaRegistrazione();
                    break;

                case "0":
                    System.out.println("Uscita dal programma in corso...");
                    exit = true;
                    break;

                default:
                    System.out.println("Operazione non consentita. Riprova.");
            }
        }
    }

    private void stampaMenu() {
        pulisciConsole();

        scriviMessaggio("================================");
        scriviMessaggio("          THE KNIFE             ");
        scriviMessaggio("================================");
        scriviMessaggio("1 | Login");
        scriviMessaggio("2 | Continua come ospite");
        scriviMessaggio("3 | Registrazione");
        scriviMessaggio("0 | Esci");
    }

    private void vaiAllaLogin() {
        LoginPage loginPage = new LoginPage(scanner);
        loginPage.start();
    }

    private void continuaComeGuest() {
        GuestPage guestPage = new GuestPage(scanner);
        guestPage.start();
    }

    private void vaiAllaRegistrazione() {
        RegistrazionePage registrazionePage = new RegistrazionePage(scanner);
        registrazionePage.start();
    }

}
