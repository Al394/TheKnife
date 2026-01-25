package theknife.navigation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import theknife.businessLogic.BLRistoranti;
import theknife.enums.Enums.TernaryInfo;
import theknife.enums.Enums.FasceDiPrezzoOp;
import theknife.models.Cliente;
import theknife.models.Coordinate;
import theknife.models.FiltroRicerca;
import theknife.models.FiltroRicerca.FiltroPrezzo;
import theknife.models.Ristorante;

/**
 * Controller per la modalità Guest (ospite).
 * Consente solo la consultazione dei ristoranti.
 */
public class GuestPage extends Navigation {

  private Cliente clienteNonAutenticato = new Cliente();

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

        case "2":
          // visualizzaTutti();
          break;

        case "0":
          exit = true;
          break;

        default:
          stampaErrore("Scelta non valida.");
      }
    }
  }

  /**
   * Menu guest.
   */
  private void stampaMenu() {
    System.out.println("==============================");
    System.out.println("      MODALITÀ OSPITE         ");
    System.out.println("==============================");
    System.out.println("1. Cerca ristoranti");
    System.out.println("2. Visualizza tutti i ristoranti");
    System.out.println("0. Torna alla Home");
    System.out.print("Seleziona un'opzione: ");
  }

  private void cercaRistoranti() {
    BLRistoranti blRistoranti = new BLRistoranti();

    stampaInfo("Ricerca ristoranti");

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

    stampaInfo("Per proseguire imposta almeno la nazione in cui ti trovi.");
    while (nazione.isBlank()) {
      nazione = leggiInput("Nazione: ");

      if (nazione.isBlank())
        stampaErrore("Ai fini della ricerca la nazione è obbligatoria.");
    }

    stampaInfo("Invio per ignorare.");
    citta = leggiInput("Città: ");

    stampaInfo("Invio per ignorare.");
    indirizzo = leggiInput("Tipo indirizzo: ");

    stampaInfo("Invio per ignorare.");
    cucina = leggiInput("Tipo cucina: ");

    stampaInfo("Invio per ignorare.");
    String sceltaOperazione = leggiInput(
        "Filtro prezzo:\nInserisci 1 per 'Maggiore di'\nInserisci 2 per 'Minore di'\nInserisci 3 per 'Tra due valori'\nInvio per ignorare.\n");

    switch (sceltaOperazione) {
      case "1":
        operazione = FasceDiPrezzoOp.GREATER_THAN;
        prezzo1 = Integer.parseInt(leggiInput("Inserisci prezzo: da [1-4]\\n"));
        filtroPrezzo = new FiltroPrezzo(operazione, prezzo1);
        break;
      case "2":
        operazione = FasceDiPrezzoOp.LESS_THAN;
        prezzo1 = Integer.parseInt(leggiInput("Inserisci prezzo: da [1-4]\\n"));
        filtroPrezzo = new FiltroPrezzo(operazione, prezzo1);
        break;
      case "3":
        operazione = FasceDiPrezzoOp.BETWEEN;
        prezzo1 = Integer.parseInt(leggiInput("Da prezzo: da [1-4]\n "));
        prezzo2 = Integer.parseInt(leggiInput("A prezzo: da [1-4]\\n"));
        filtroPrezzo = new FiltroPrezzo(operazione, prezzo1, prezzo2);
      default:
        break;
    }

    stampaInfo("Invio per ignorare.");
    mediaStelle = Integer.parseInt(leggiInput("Media stelle: "));

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

    if (risultati.isEmpty()) {
      stampaInfo("Nessun ristorante trovato.");
    } else {
      for (Ristorante r : risultati) {
        System.out.println(r);
      }
    }

    attendiInvio();
  }
}
