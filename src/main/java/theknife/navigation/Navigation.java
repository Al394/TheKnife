package theknife.navigation;

import java.util.Scanner;

/**
 * Classe astratta base per tutti i controller dell'applicazione.
 * Fornisce funzionalità comuni di input/output.
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public abstract class Navigation {

  protected final Scanner scanner;

  public Navigation(Scanner scanner) {
    this.scanner = scanner;
  }

  /**
   * Avvia la.
   */
  public abstract void start();

  protected String leggiInput(String messaggio) {
    System.out.print(messaggio);
    return scanner.nextLine().trim();
  }

  protected void stampaMessaggio(String messaggio) {
    System.out.println(messaggio);
  }

  protected void stampaErrore(String messaggio) {
    System.out.println("Errore: " + messaggio);
  }

  protected void stampaInfo(String messaggio) {
    System.out.println("[" + messaggio + "]");
  }

  protected void attendiInvio() {
    System.out.println("\nPremi INVIO per continuare...");
    scanner.nextLine();
  }

  protected void premiInvioPerVuoto() {
    System.out.println("\nPremi INVIO per lasciare vuoto");
    scanner.nextLine();
  }

  protected boolean conferma(String messaggio) {
    System.out.print(messaggio + " (s/n): ");
    return scanner.nextLine().equalsIgnoreCase("s");
  }

  protected static void pulisciConsole() {
    // Codice ANSI: \033[H (Home) \033[2J (Clear screen)
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}
