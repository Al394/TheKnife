package theknife.navigation;

import theknife.businessLogic.BLCliente;
import theknife.models.Cliente;
import theknife.models.Recensione;
import theknife.models.Ristorante;
import theknife.utility.TheKnifeLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Controller per la visualizzazione dei ristoranti nella modalità Cliente.
 * Consente la visualizzazione dettagliata e l'aggiunta ai preferiti.
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class DettagliRistorantiClientePage extends Navigation {
    public final ArrayList<Ristorante> risultati = new ArrayList<>();
    private final Cliente cliente;
    private final BLCliente blCliente;

    /**
     * Costruttore
     *
     * @param scanner
     * @param risultati
     * @param cliente
     */
    public DettagliRistorantiClientePage(Scanner scanner, ArrayList<Ristorante> risultati, Cliente cliente) {
        super(scanner);
        this.risultati.addAll(risultati);
        this.cliente = cliente;
        this.blCliente = new BLCliente(cliente);
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

                    input = leggiInput("Seleziona un'opzione: ");

                    switch (input) {
                        case "0":
                            return;
                        case "1":
                            stampaMenuDettagliRistorante();

                            scriviDettagliCompletiRistorante(ristoranteSelezionato);
                            break;
                        case "2":
                            visualizzaRecensioni(ristoranteSelezionato);
                            break;
                        case "3":
                            List<Integer> ids = cliente.getRistorantiIDs();
                            int id = ristoranteSelezionato.getId();

                            Boolean res = ids.contains(id);

                            if (res) {
                                // Rimuovi dai preferiti
                                if (conferma("Rimuovere dai preferiti?")) {
                                    try {
                                        blCliente.rimuoviPreferito(ristoranteSelezionato.getId());
                                    } catch (IOException e) {
                                        TheKnifeLogger.error(e);

                                        scriviErrore("Errore nel rimuovere il preferito.");
                                    }
                                    scriviMessaggio("Rimosso dai preferiti!");
                                }
                            } else {
                                // Aggiungi ai preferiti
                                try {
                                    blCliente.aggiungiPreferito(ristoranteSelezionato.getId());
                                } catch (IOException e) {
                                    TheKnifeLogger.error(e);

                                    scriviErrore("Errore nel salvataggio preferito.");
                                }
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

        ArrayList<Recensione> recensioniRistorante = ristorante.getRecensioni();

        if (ristorante.getRecensioni().isEmpty()) {
            scriviInfo("Nessuna recensione per questo ristorante.");
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
    }
}
