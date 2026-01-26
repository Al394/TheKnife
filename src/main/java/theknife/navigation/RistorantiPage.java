package theknife.navigation;

import theknife.models.Recensione;
import theknife.models.Ristorante;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RistorantiPage extends Navigation {
    public final List<Ristorante> risultati = new ArrayList<>();

    public RistorantiPage(Scanner scanner) {
        super(scanner);
    }

    public RistorantiPage(Scanner scanner, ArrayList<Ristorante> risultati) {
        super(scanner);
        this.risultati.addAll(risultati);
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
                    "Inserisci l'indice del ristorante per selezionare un ristorante.\n0 | Torna indietro.");
            if (input.equals("0"))
                return;

            if (!input.isBlank()) {
                try {
                    int indice = Integer.parseInt(input) - 1;
                    if (indice < 0 || indice >= risultati.size()) {
                        scriviErrore("Indice non valido. Riprova.");
                        continue;
                    }

                    Ristorante ristoranteSelezionato = risultati.get(indice);

                    pulisciConsole();

                    input = leggiInput(
                            "1 | Visualizza i dettagli del ristorante.\n2 | Visualizza le recensioni.\n0 | Torna indietro.");

                    if (input.equals("0"))
                        return;
                    if (input.equals("1")) {
                        stampaMenuDettagliRistorante();
                        scriviDettagliCompletiRistorante(ristoranteSelezionato);
                    }
                    if (input.equals("2")) {
                        stampaMenuDettagliRecensioni();
                        List<Recensione> recensioni = ristoranteSelezionato.getRecensioni();

                        for (Recensione recensione : recensioni) {
                            scriviMessaggio(recensione.toString());
                        }
                    }

                    attendiInputBack();

                    return;

                } catch (NumberFormatException e) {
                    scriviErrore("Input non valido. Inserisci un numero.");
                }
            }
        }
    }

    /**
     * Menu guest.
     */
    private void stampaMenu() {
        pulisciConsole();

        scriviMessaggio("==============================");
        scriviMessaggio("    Risultati della ricerca   ");
        scriviMessaggio("==============================");
    }

    private void stampaMenuDettagliRistorante() {
        pulisciConsole();

        scriviMessaggio("==============================");
        scriviMessaggio("      Dettagli Ristorante     ");
        scriviMessaggio("==============================");
    }

    private void stampaMenuDettagliRecensioni() {
        pulisciConsole();

        scriviMessaggio("==============================");
        scriviMessaggio("      Dettagli Recensioni     ");
        scriviMessaggio("==============================");
    }

}
