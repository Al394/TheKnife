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

      String scelta = scanner.nextLine();

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

    System.out.println("================================");
    System.out.println("          THE KNIFE             ");
    System.out.println("================================");
    System.out.println("1 | Login");
    System.out.println("2 | Continua come ospite");
    System.out.println("3 | Registrazione");
    System.out.println("0 | Esci");
    System.out.print("Seleziona un'opzione: ");
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
