package theknife.navigation;

import theknife.models.Ristorante;

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

    protected static void pulisciConsole() {
        // Codice ANSI: \033[H (Home) \033[2J (Clear screen)
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Metodo astratto che deve essere implementato da tutte le pagine di
     * navigazione.
     */
    public abstract void start();

    protected String leggiInput(String messaggio) {
        System.out.print(messaggio);
        return scanner.nextLine().trim();
    }

    protected void scriviMessaggio(String messaggio) {
        System.out.println(messaggio);
    }

    protected void scriviDettagliRistorante(int indice, Ristorante ristorante) {
        System.out.println(indice + ". " + ristorante.toString() + "\n");
    }

    protected void scriviDettagliCompletiRistorante(Ristorante ristorante) {
        System.out.println(ristorante.getDettagliCompleti() + "\n");
    }

    protected void scriviErrore(String messaggio) {
        System.out.println("Errore: " + messaggio);
    }

    protected void scriviInfo(String messaggio) {
        System.out.println("[" + messaggio + "]");
    }

    protected void attendiInputBack() {
        System.out.println("\nPremi Invio per tornare indietro.");
        scanner.nextLine();
    }

    protected boolean conferma(String messaggio) {
        System.out.print(messaggio + " (s/n): ");
        return scanner.nextLine().equalsIgnoreCase("s");
    }
}
