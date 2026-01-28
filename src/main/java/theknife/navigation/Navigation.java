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

    private static final String NO_BLANK_INPUT = "Questo campo è obbligatorio.";
    protected final Scanner scanner;

    public Navigation(Scanner scanner) {
        this.scanner = scanner;
    }

    // Codice ANSI che permette la pulizia della console (anche del CMD windows)
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

    protected String loopLeggiInput(String messaggio) {
        String input = "";
        while (true) {
            System.out.print(messaggio);

            input = scanner.nextLine().trim();

            if (input.isBlank())
                scriviMessaggio(NO_BLANK_INPUT);
            else
                return input;
        }
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
        System.out.println("Premi Invio per tornare indietro.");
        scanner.nextLine();
    }

    protected boolean conferma(String messaggio) {
        System.out.print(messaggio + " (s/n): ");
        return scanner.nextLine().equalsIgnoreCase("s");
    }
}
