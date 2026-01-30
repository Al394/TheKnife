package theknife.navigation;

import theknife.businessLogic.BLRistorante;
import theknife.businessLogic.BLRistoratore;
import theknife.enums.Enums;
import theknife.enums.Enums.Risposte;
import theknife.models.Recensione;
import theknife.models.Ristorante;
import theknife.models.Ristoratore;
import theknife.models.Utente;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Controller per i Ristoratore.
 * Consente di la navigazione menu da parte del ristoratore.
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class RistoratorePage extends Navigation {

    private Ristoratore ristoratore;
    private BLRistorante blRistorante;

    public RistoratorePage(Scanner scanner, Utente ristoratore) {
        super(scanner);
        this.ristoratore = (Ristoratore) ristoratore;
        this.blRistorante = new BLRistorante();
    }

    @Override
    public void start() {
        boolean exit = false;

        while (!exit) {
            stampaMenu();

            String scelta = loopLeggiInput("Seleziona un'opzione: ");

            switch (scelta) {
                case "1":
                    aggiungiRistorante();
                    break;

                case "2":
                    visualizzaRiepilogo();
                    break;

                case "3":
                    visualizzaRecensioni();
                    break;

                case "4":
                    rispostaRecensioni();
                    break;

                case "0":
                    scriviMessaggio("Logout in corso...");
                    exit = true;
                    break;

                default:
                    scriviMessaggio("Operazione non consentita. Riprova.");
            }
        }
    }

    /**
     * Stampa il menu principale della pagina ristoratore.
     */
    private void stampaMenu() {
        pulisciConsole();

        scriviMessaggio("================================");
        scriviMessaggio("        PAGINA RISTORATORE      ");
        scriviMessaggio("================================");
        scriviMessaggio("1 | Aggiungi Ristorante");
        scriviMessaggio("2 | Visualizza i miei Ristoranti");
        scriviMessaggio("3 | Visualizza Recensioni");
        scriviMessaggio("4 | Rispondi Recensioni");
        scriviMessaggio("0 | Logout");
    }

    /**
     *
     * Aggiunge un nuovo ristorante ai ristoranti gestiti dall'utente ristoratore.
     */
    private void aggiungiRistorante() {
        String nome;
        String nazione;
        String citta;
        String indirizzo;
        String tipoCucina;
        String descrizione;
        String servizi;

        double latitudine = 0;
        double longitudine = 0;
        boolean inputValido = false;
        boolean delivery;
        boolean booking;
        int prezzoMedio = 0;

        stampaAggiungiRistorante();

        try {
            nome = loopLeggiInput("Nome ristorante: ");
            nazione = loopLeggiInput("Nazione: ");
            citta = loopLeggiInput("Città: ");
            indirizzo = loopLeggiInput("Indirizzo: ");

            while (!inputValido) {
                try {
                    String latInput = loopLeggiInput("Latitudine: ");
                    latitudine = Double.parseDouble(latInput);
                    inputValido = true;
                } catch (NumberFormatException e) {
                    scriviErrore("Latitudine non valida. Inserire un numero.");
                }
            }

            inputValido = false;
            while (!inputValido) {
                try {
                    String lonInput = loopLeggiInput("Longitudine: ");
                    longitudine = Double.parseDouble(lonInput);
                    inputValido = true;
                } catch (NumberFormatException e) {
                    scriviErrore("Longitudine non valida. Inserire un numero.");
                }
            }

            inputValido = false;
            while (!inputValido) {
                try {
                    String prezzoInput = loopLeggiInput("Prezzo medio (Euro): ");
                    prezzoMedio = Integer.parseInt(prezzoInput);
                    inputValido = true;
                } catch (NumberFormatException e) {
                    scriviErrore("Prezzo non valido. Inserire un numero intero.");
                }
            }

            delivery = conferma("Il ristorante offre il servizio di delivery?");
            booking = conferma("Il ristorante offre prenotazioni online?");

            tipoCucina = loopLeggiInput("Tipo di cucina: ");
            descrizione = loopLeggiInput("Descrizione: ");
            servizi = loopLeggiInput("Servizi disponibili: ");

            BLRistorante blRistorante = new BLRistorante();
            BLRistoratore blRistoratore = new BLRistoratore(ristoratore);

            Ristorante nuovoRistorante = blRistorante.aggiungiRistorante(nome, nazione, citta, indirizzo, latitudine,
                    longitudine, prezzoMedio, delivery, booking, tipoCucina, descrizione, servizi);

            blRistoratore.aggiungiRiferimentoRistorante(nuovoRistorante.getId());

            scriviMessaggio("Ristorante aggiunto con successo!");
            attendiInputBack();
        } catch (Exception e) {
            scriviErrore("Errore nell'aggiunta del ristorante: " + e.getMessage());
            attendiInputBack();
        }
    }

    private void stampaAggiungiRistorante() {
        pulisciConsole();

        scriviMessaggio("================================");
        scriviMessaggio("    AGGIUNTA NUOVO RISTORANTE   ");
        scriviMessaggio("================================");
    }

    /**
     * Visualizza il riepilogo delle recensioni per i ristoranti del ristoratore.
     * Mostra media stelle e numero di recensioni per ogni ristorante.
     */
    public void visualizzaRiepilogo() {
        pulisciConsole();

        scriviMessaggio("================================");
        scriviMessaggio("      RIEPILOGO RIRSTORANTI     ");
        scriviMessaggio("================================");

        try {
            ArrayList<Integer> mieRistorantiIDs = ristoratore.getRistorantiIDs();

            if (mieRistorantiIDs == null || mieRistorantiIDs.isEmpty()) {
                scriviMessaggio("Non hai ancora aggiunto alcun ristorante.");
                attendiInputBack();
                return;
            }

            ArrayList<Ristorante> mieRistoranti = blRistorante.getRistorantiRistoratore(mieRistorantiIDs);

            for (Ristorante ristorante : mieRistoranti) {
                double[] riepilogo = blRistorante.getRiepilogoRecensioni(ristorante.getId());
                double mediaStelle = riepilogo[0];
                int numeroRecensioni = (int) riepilogo[1];

                scriviMessaggio("Ristorante: " + ristorante.getNome());
                scriviMessaggio("Città: " + ristorante.getCitta());
                scriviMessaggio("Media stelle: " + String.format("%.2f", mediaStelle) + "/5");
                scriviMessaggio("Numero recensioni: " + numeroRecensioni);
                scriviMessaggio("================================");
            }

            attendiInputBack();

        } catch (Exception e) {
            scriviErrore("Errore nella visualizzazione del riepilogo: " + e.getMessage());
            attendiInputBack();
        }
    }

    /**
     * Visualizza le recensioni dei propri ristoranti con il dettaglio delle stelle.
     * L'utente può selezionare quale ristorante visualizzare.
     */
    public void visualizzaRecensioni() {
        pulisciConsole();

        scriviMessaggio("================================");
        scriviMessaggio("        LE MIE RECENSIONI       ");
        scriviMessaggio("================================");

        try {
            ArrayList<Integer> mieRistorantiIDs = ristoratore.getRistorantiIDs();

            if (mieRistorantiIDs == null || mieRistorantiIDs.isEmpty()) {
                scriviMessaggio("Non hai ancora aggiunto alcun ristorante.");
                attendiInputBack();
                return;
            }

            ArrayList<Ristorante> mieRistoranti = blRistorante.getRistorantiRistoratore(mieRistorantiIDs);

            // Selezione del ristorante
            scriviMessaggio("Seleziona un ristorante:");
            for (int i = 0; i < mieRistoranti.size(); i++) {
                scriviMessaggio((i + 1) + ". " + mieRistoranti.get(i).getNome() + " (" +
                        mieRistoranti.get(i).getCitta() + ")");
            }

            int scelta = 0;
            boolean inputValido = false;
            while (!inputValido) {
                try {
                    String input = leggiInput("\nScelta (numero): ");
                    scelta = Integer.parseInt(input);
                    if (scelta < 1 || scelta > mieRistoranti.size()) {
                        scriviErrore("Scelta non valida.");
                        continue;
                    }
                    inputValido = true;
                } catch (NumberFormatException e) {
                    scriviErrore("Inserire un numero valido.");
                }
            }

            Ristorante ristoranteScelto = mieRistoranti.get(scelta - 1);
            ArrayList<Recensione> recensioni = blRistorante.getRecensioniRistorante(ristoranteScelto.getId());

            pulisciConsole();

            scriviMessaggio("================================");
            scriviMessaggio("RECENSIONI PER: " + ristoranteScelto.getNome().toUpperCase());
            scriviMessaggio("================================");

            if (recensioni.isEmpty()) {
                scriviMessaggio("Non ci sono ancora recensioni per questo ristorante.");
            } else {
                for (int i = 0; i < recensioni.size(); i++) {
                    Recensione rec = recensioni.get(i);
                    scriviMessaggio("Recensione #" + (i + 1));
                    scriviMessaggio("Stelle: " + rec.getStelle() + "/5");
                    scriviMessaggio("Commento: " + rec.getCommento());

                    if (!rec.getRisposta().equals(Enums.Risposte.NESSUNA_RISPOSTA.getRisposta()))
                        scriviMessaggio("Risposta: " + rec.getRisposta());
                }
            }

            attendiInputBack();

        } catch (Exception e) {
            scriviErrore("Errore nella visualizzazione delle recensioni: " + e.getMessage());
            attendiInputBack();
        }
    }

    /**
     * Consente al ristoratore di rispondere alle recensioni dei propri ristoranti.
     * Massimo una risposta per ogni recensione.
     */
    public void rispostaRecensioni() {
        pulisciConsole();

        scriviMessaggio("================================");
        scriviMessaggio("    RISPONDI ALLE RECENSIONI    ");
        scriviMessaggio("================================");

        try {
            ArrayList<Integer> mieRistorantiIDs = ristoratore.getRistorantiIDs();

            if (mieRistorantiIDs == null || mieRistorantiIDs.isEmpty()) {
                scriviMessaggio("Non hai ancora aggiunto alcun ristorante.");
                attendiInputBack();
                return;
            }

            ArrayList<Ristorante> mieRistoranti = blRistorante.getRistorantiRistoratore(mieRistorantiIDs);

            // Selezione del ristorante
            scriviMessaggio("Seleziona un ristorante:");
            for (int i = 0; i < mieRistoranti.size(); i++) {
                scriviMessaggio((i + 1) + ". " + mieRistoranti.get(i).getNome() + " (" +
                        mieRistoranti.get(i).getCitta() + ")");
            }

            int sceltaRistorante = 0;
            boolean inputValido = false;
            while (!inputValido) {
                try {
                    String input = leggiInput("\nScelta ristorante (numero): ");
                    sceltaRistorante = Integer.parseInt(input);
                    if (sceltaRistorante < 1 || sceltaRistorante > mieRistoranti.size()) {
                        scriviErrore("Scelta non valida.");
                        continue;
                    }
                    inputValido = true;
                } catch (NumberFormatException e) {
                    scriviErrore("Inserire un numero valido.");
                }
            }

            Ristorante ristoranteScelto = mieRistoranti.get(sceltaRistorante - 1);
            ArrayList<Recensione> recensioni = blRistorante.getRecensioniRistorante(ristoranteScelto.getId());

            if (recensioni.isEmpty()) {
                scriviMessaggio("Non ci sono recensioni da cui rispondere.");
                attendiInputBack();
                return;
            }

            pulisciConsole();
            scriviMessaggio("================================");
            scriviMessaggio("SCEGLI QUALE RECENSIONE RISPONDERE");
            scriviMessaggio("================================");

            // Mostra le recensioni senza risposta o tutte le recensioni
            ArrayList<Recensione> recensioniSenzaRisposta = new ArrayList<>();
            for (Recensione rec : recensioni) {
                if (rec.getRisposta().equals(Risposte.NESSUNA_RISPOSTA.getRisposta())) {
                    recensioniSenzaRisposta.add(rec);
                }
            }

            if (recensioniSenzaRisposta.isEmpty()) {
                scriviMessaggio("Hai già risposto a tutte le recensioni!");
                attendiInputBack();
                return;
            }

            for (int i = 0; i < recensioniSenzaRisposta.size(); i++) {
                Recensione rec = recensioniSenzaRisposta.get(i);
                scriviMessaggio((i + 1) + ". " + rec.getStelle() + "/5 - \"" +
                        (rec.getCommento().length() > 50 ? rec.getCommento().substring(0, 50) + "..."
                                : rec.getCommento())
                        + "\"");
            }

            int sceltaRecensione = 0;
            inputValido = false;
            while (!inputValido) {
                try {
                    String input = leggiInput("\nScelta (numero): ");
                    sceltaRecensione = Integer.parseInt(input);
                    if (sceltaRecensione < 1 || sceltaRecensione > recensioniSenzaRisposta.size()) {
                        scriviErrore("Scelta non valida.");
                        continue;
                    }
                    inputValido = true;
                } catch (NumberFormatException e) {
                    scriviErrore("Inserire un numero valido.");
                }
            }

            Recensione recensioneScelto = recensioniSenzaRisposta.get(sceltaRecensione - 1);

            pulisciConsole();
            scriviMessaggio("================================");
            scriviMessaggio("    INSERISCI LA TUA RISPOSTA   ");
            scriviMessaggio("================================");
            scriviMessaggio("Recensione originale (" + recensioneScelto.getStelle() + "/5):");
            scriviMessaggio("\"" + recensioneScelto.getCommento() + "\"\n");

            String risposta = leggiInput("La tua risposta: ");

            blRistorante.rispondiRecensione(ristoranteScelto, recensioneScelto, risposta);

            scriviMessaggio("Risposta aggiunta con successo!");

            attendiInputBack();

        } catch (Exception e) {
            scriviErrore("Errore nella risposta alle recensioni: " + e.getMessage());
            attendiInputBack();
        }
    }

}
