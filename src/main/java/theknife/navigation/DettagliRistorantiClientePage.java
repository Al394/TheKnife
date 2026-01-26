package theknife.navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import theknife.models.Cliente;
import theknife.models.Recensione;
import theknife.models.Ristorante;
import theknife.utility.RecensioniManager;
import theknife.utility.UtentiManager;

/**
 * Controller per la visualizzazione dei ristoranti nella modalità Cliente.
 * Consente la visualizzazione dettagliata e l'aggiunta ai preferiti.
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class DettagliRistorantiClientePage extends Navigation {
  public final ArrayList<Ristorante> risultati = new ArrayList<>();
  private Cliente cliente;

  public DettagliRistorantiClientePage(Scanner scanner, ArrayList<Ristorante> risultati, Cliente cliente) {
    super(scanner);
    this.risultati.addAll(risultati);
    this.cliente = cliente;
  }

  @Override
  public void start() {
    String input;

    while (true) {
      stampaMenu();

      scriviMessaggio("Trovati " + risultati.size() + " ristoranti:");

      for (Ristorante ristorante : risultati) {
        scriviDettagliRistorante(risultati.indexOf(ristorante) + 1, ristorante);
      }

      input = leggiInput(
          "Inserisci l'indice del ristorante per selezionare un ristorante.\n0 | Torna indietro.\n");
      if (input.equals("0"))
        return;

      if (!input.isBlank()) {
        try {
          int indice = Integer.parseInt(input) - 1;

          // valido l'input
          if (indice < 0 || indice >= risultati.size()) {
            scriviErrore("Indice non valido. Riprova.");
            continue;
          }

          // Seleziono ristorante da input.
          Ristorante ristoranteSelezionato = risultati.get(indice);

          stampaMenuRistoranteSelezionato(ristoranteSelezionato);

          input = leggiInput("");

          switch (input) {
            case "0":
              return;
            case "1":
              stampaMenuDettagliRistorante();

              scriviDettagliCompletiRistorante(ristoranteSelezionato);
              break;
            case "2":
              visualizzaRecensioni(ristoranteSelezionato);
            case "3":
              if (cliente.getRistorantiIDs().contains(ristoranteSelezionato.getId())) {
                // Rimuovi dai preferiti
                if (conferma("Rimuovere dai preferiti?")) {
                  cliente.getRistorantiIDs().remove(Integer.valueOf(ristoranteSelezionato.getId()));

                  aggiornaUtente();

                  scriviMessaggio("Rimosso dai preferiti!");
                }
              } else {
                // Aggiungi ai preferiti
                cliente.getRistorantiIDs().add(ristoranteSelezionato.getId());
                aggiornaUtente();
                scriviMessaggio("Aggiunto ai preferiti!");
              }
              break;

            default:
              break;
          }

          attendiInputBack();
        } catch (NumberFormatException e) {
          scriviErrore("Input non valido.");
        }
      }
    }

  }

  private void stampaMenuRistoranteSelezionato(Ristorante ristoranteSelezionato) {
    pulisciConsole();

    if (cliente.getRistorantiIDs().contains(ristoranteSelezionato.getId())) {
      scriviMessaggio("1 | Visualizza i dettagli del ristorante.");
      scriviMessaggio("2 | Visualizza le recensioni.");
      scriviMessaggio("3 | Rimuovi dai preferiti.");
      scriviMessaggio("0 | Torna indietro.");
    } else {
      scriviMessaggio("1 | Visualizza i dettagli del ristorante.");
      scriviMessaggio("2 | Visualizza le recensioni.");
      scriviMessaggio("3 | Aggiungi ai preferiti.");
      scriviMessaggio("0 | Torna indietro.");
    }
  }

  private void stampaMenu() {
    pulisciConsole();

    System.out.println("==============================");
    System.out.println("       RISTORANTI TROVATI     ");
    System.out.println("==============================");
  }

  private void stampaMenuDettagliRistorante() {
    pulisciConsole();

    System.out.println("==============================");
    System.out.println("      DETTAGLI RISTORANTE     ");
    System.out.println("==============================");
  }

  private void visualizzaRecensioni(Ristorante ristorante) {
    pulisciConsole();

    RecensioniManager rm = RecensioniManager.getInstance();
    HashMap<Integer, Recensione> tuteRecensioni = rm.getRecensioni();

    ArrayList<Recensione> recensioniRistorante = new ArrayList<>();
    for (Recensione r : tuteRecensioni.values()) {
      if (r.getRistoranteID() == ristorante.getId()) {
        recensioniRistorante.add(r);
      }
    }

    if (recensioniRistorante.isEmpty()) {
      scriviInfo("Nessuna recensione per questo ristorante.");
      attendiInputBack();
      return;
    }

    scriviMessaggio("Recensioni per " + ristorante.getNome() + ":");

    for (Recensione r : recensioniRistorante) {
      System.out.println("\n" + r.getStelle() + " stelle:");
      System.out.println(r.getCommento());
      if (r.getRisposta() != null && !r.getRisposta().isEmpty()) {
        System.out.println("[Risposta]: " + r.getRisposta());
      }
    }

    attendiInputBack();
  }

  private void aggiornaUtente() {
    try {
      UtentiManager.scriviUtenti(new ArrayList<>(UtentiManager.getUtenti().values()));
    } catch (Exception e) {
      scriviErrore("Errore durante l'aggiornamento dei dati.");
    }
  }
}
