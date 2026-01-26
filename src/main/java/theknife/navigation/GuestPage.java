package theknife.navigation;

import theknife.businessLogic.BLRistorante;
import theknife.enums.Enums.FasceDiPrezzoOp;
import theknife.enums.Enums.TernaryInfo;
import theknife.models.FiltroRicerca;
import theknife.models.FiltroRicerca.FiltroPrezzo;
import theknife.models.Ristorante;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Controller per la modalità Guest (ospite).
 * Consente solo la consultazione dei ristoranti.
 */
public class GuestPage extends Navigation {

    public GuestPage(Scanner scanner) {
        super(scanner);
    }

    /**
     * Avvia la modalità Guest.
     */
    @Override
    public void start() {
        boolean exit = false;

        while (!exit) {
            stampaMenu();

            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1":
                    cercaRistoranti();
                    break;

                case "0":
                    exit = true;
                    break;

                default:
                    scriviErrore("Scelta non valida.");
            }
        }
    }

    /**
     * Menu guest.
     */
    private void stampaMenu() {
        pulisciConsole();

        System.out.println("==============================");
        System.out.println("      MODALITÀ OSPITE         ");
        System.out.println("==============================");
        System.out.println("1 | Cerca ristoranti");
        System.out.println("0 | Torna alla Home");
        System.out.print("Seleziona un'opzione: ");
    }

    private void cercaRistoranti() {

        BLRistorante blRistoranti = new BLRistorante();

        stampaMenuRicercaRistoranti();

        String nazione = null;
        String citta = null;
        String indirizzo = null;
        String cucina = null;
        FiltroPrezzo filtroPrezzo = null;
        FasceDiPrezzoOp operazione = null;
        int prezzo1 = -1;
        int prezzo2 = -1;
        TernaryInfo delivery = TernaryInfo.ANY;
        TernaryInfo booking = TernaryInfo.ANY;
        int mediaStelle = 0;

        scriviInfo("Inserisci la nazione in cui ti trovi.");
        while (nazione == null || nazione.isBlank()) {
            nazione = leggiInput("Nazione: ");

            if (nazione.isBlank())
                scriviErrore("Ai fini della ricerca la nazione è obbligatoria.");
        }

        scriviInfo("Inserisci la città in cui ti trovi.");
        while (citta == null || citta.isBlank()) {
            citta = leggiInput("Città: ");

            if (citta.isBlank())
                scriviErrore("Ai fini della ricerca la città è obbligatoria.");
        }

        scriviInfo("Invio per ignorare.");
        indirizzo = leggiInput("Tipo indirizzo: ");

        scriviInfo("Invio per ignorare.");
        cucina = leggiInput("Tipo cucina: ");

        scriviInfo("Invio per ignorare.");
        scriviInfo(" Seleziona un opzione ");
        scriviMessaggio("1 | Maggiore di\n2 | Minore di\n3 | Tra due valori");

        String sceltaOperazione = leggiInput("Filtro prezzo: ");

        switch (sceltaOperazione) {
            case "1":
                operazione = FasceDiPrezzoOp.GREATER_THAN;
                prezzo1 = Integer.parseInt(leggiInput("Inserisci prezzo: "));
                filtroPrezzo = new FiltroPrezzo(operazione, prezzo1);
                break;
            case "2":
                operazione = FasceDiPrezzoOp.LESS_THAN;
                prezzo1 = Integer.parseInt(leggiInput("Inserisci prezzo: "));
                filtroPrezzo = new FiltroPrezzo(operazione, prezzo1);
                break;
            case "3":
                operazione = FasceDiPrezzoOp.BETWEEN;
                prezzo1 = Integer.parseInt(leggiInput("Da prezzo: "));
                prezzo2 = Integer.parseInt(leggiInput("A prezzo: "));
                filtroPrezzo = new FiltroPrezzo(operazione, prezzo1, prezzo2);
            default:
                break;
        }

        scriviInfo("Invio per ignorare.");
        String inputDelivery = leggiInput("Consegna a domicilio? (s/n): ");
        if (inputDelivery.equalsIgnoreCase("s")) {
            delivery = TernaryInfo.YES;
        } else if (inputDelivery.equalsIgnoreCase("n")) {
            delivery = TernaryInfo.NO;
        } else {
            delivery = TernaryInfo.ANY;
        }

        scriviInfo("Invio per ignorare.");
        String inputBooking = leggiInput("Prenotazione online? (s/n): ");
        if (inputBooking.equalsIgnoreCase("s"))
            booking = TernaryInfo.YES;
        else if (inputDelivery.equalsIgnoreCase("n"))
            booking = TernaryInfo.NO;
        else
            booking = TernaryInfo.ANY;

        scriviInfo("Invio per ignorare.");
        String mediaStelleStr = leggiInput("Media stelle: ");

        if (mediaStelleStr.isBlank())
            mediaStelle = -1;
        else
            mediaStelle = Integer.parseInt(mediaStelleStr);

        FiltroRicerca filtro = new FiltroRicerca(
                nazione.trim(),
                citta.trim(),
                indirizzo.trim(),
                null,
                cucina.trim(),
                filtroPrezzo,
                delivery,
                booking,
                mediaStelle);

        ArrayList<Ristorante> risultati = blRistoranti.cercaRistorante(filtro);

        pulisciConsole();

        if (risultati.isEmpty()) {
            pulisciConsole();

            scriviInfo("Nessun ristorante trovato");

            attendiInputBack();
        } else {
            RistorantiPage ristorantePage = new RistorantiPage(scanner, risultati);
            ristorantePage.start();
        }
    }

    private void stampaMenuRicercaRistoranti() {
        pulisciConsole();

        scriviMessaggio("==============================");
        scriviMessaggio("      RICERCA RISTORANTI      ");
        scriviMessaggio("==============================");
    }
}
