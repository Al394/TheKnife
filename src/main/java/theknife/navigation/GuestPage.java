package theknife.navigation;

import java.util.List;
import java.util.Scanner;

import theknife.businessLogic.BLRistoranti;
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

      ottieniInformazioniPreliminari();

      stampaMenu();
      String scelta = scanner.nextLine();

      switch (scelta) {
        case "1":
          cercaRistoranti();
          break;

        case "2":
          visualizzaTutti();
          break;

        case "0":
          exit = true;
          break;

        default:
          stampaErrore("Scelta non valida.");
      }
    }
  }

  private void ottieniInformazioniPreliminari() {
    stampaInfo("Per proseguire imposta almeno la nazione in cui ti trovi.");
    while (clienteNonAutenticato.getNazione().isBlank()) {
      clienteNonAutenticato.setNazione(leggiInput("Nazione: "));

      if (clienteNonAutenticato.getNazione().isBlank())
        stampaErrore("Ai fini della ricerca la nazione è obbligatoria.");
    }

    stampaInfo("Per saltare premere invio.");
    citta = (leggiInput("Città: "));
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
    double prezzoMax = -1;
    boolean delivery;
    boolean booking;

    stampaInfo("Per proseguire imposta almeno la nazione in cui ti trovi.");
    while (nazione.isBlank()) {
      nazione = leggiInput("Nazione: ");

      if (nazione.isBlank())
        stampaErrore("Ai fini della ricerca la nazione è obbligatoria.");
    }

    stampaInfo("Invio per ignorare.");
    citta = leggiInput("Città: ");

    indirizzo = leggiInput("Tipo indirizzo: ");

    cucina = leggiInput("Tipo cucina: ");

    prezzoMax = Double.parseDouble(leggiInput("Prezzo massimo [1-4]: "));

    FiltroRicerca filtro = new FiltroRicerca(clienteNonAutenticato.getNazione(), clienteNonAutenticato.getCitta(), null,
        null,
        cucina,
        prezzoMax > 0 ? new FiltroRicerca.FiltroPrezzo(FiltroRicerca.FiltroPrezzo.Operatore.MINORE, (int) prezzoMax)
            : null,
        false,
        false,
        (byte) 0);

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
