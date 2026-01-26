package theknife.navigation;

import theknife.businessLogic.BLCliente;
import theknife.businessLogic.BLRecensione;
import theknife.businessLogic.BLRistorante;
import theknife.enums.Enums.FasceDiPrezzoOp;
import theknife.enums.Enums.TernaryInfo;
import theknife.exceptions.ValidationException;
import theknife.models.*;
import theknife.models.FiltroRicerca.FiltroPrezzo;
import theknife.utility.RecensioniManager;
import theknife.utility.RitstorantiManager;
import theknife.utility.TheKnifeLogger;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Controller per la modalità Cliente.
 * Consente ricerca ristoranti, gestione preferiti e gestione recensioni.
 */
public class ClientePage extends Navigation {

    private final Cliente cliente;
    private final BLCliente blCliente;
    private final RecensioniManager rm = RecensioniManager.getInstance();

    public ClientePage(Scanner scanner, Utente utente) {
        super(scanner);
        this.cliente = (Cliente) utente;
        this.blCliente = new BLCliente(cliente);
    }

    /**
     * Avvia la pagina cliente.
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
                    visualizzaPreferiti();
                    break;

                case "3":
                    gestisciRecensioni();
                    break;

                case "0":
                    scriviMessaggio("Ritorno alla Home...");
                    exit = true;
                    break;

                default:
                    scriviErrore("Scelta non valida.");
            }
        }
    }

    /**
     * Menu cliente.
     */
    private void stampaMenu() {
        pulisciConsole();

        scriviMessaggio("==============================");
        scriviMessaggio("         AREA CLIENTE         ");
        scriviMessaggio("==============================");
        scriviMessaggio("  | Benvenuto, %s !".formatted(cliente.getNome()));
        scriviMessaggio("1 | Cerca ristoranti");
        scriviMessaggio("2 | Visualizza preferiti");
        scriviMessaggio("3 | Gestisci recensioni");
        scriviMessaggio("0 | Logout");
        System.out.print("Seleziona un'opzione: ");
    }

    /**
     * Gestisce la ricerca dei ristoranti con la possibilità di aggiungerli ai
     * preferiti.
     */
    private void cercaRistoranti() {

        BLRistorante blRistoranti = new BLRistorante();

        stampaMenuRicercaRistoranti();

        String nazione = cliente.getNazione();
        String citta = cliente.getCitta();
        String indirizzo = null;
        String cucina = null;
        FiltroPrezzo filtroPrezzo = null;
        FasceDiPrezzoOp operazione = null;
        int prezzo1 = -1;
        int prezzo2 = -1;
        TernaryInfo delivery = TernaryInfo.ANY;
        TernaryInfo booking = TernaryInfo.ANY;
        int mediaStelle = 0;

        scriviInfo("Filtri base utente:");
        scriviMessaggio("Nazione: %s\nCittà: %s".formatted(nazione, citta));
        scriviMessaggio("==============================");

        scriviInfo("Invio per ignorare");
        indirizzo = leggiInput("Indirizzo: ");

        scriviInfo("Invio per ignorare");
        cucina = leggiInput("Tipo cucina: ");

        scriviInfo("Invio per ignorare");
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

        scriviInfo("Invio per ignorare");
        String inputDelivery = leggiInput("Consegna a domicilio? (s/n): ");
        if (inputDelivery.equalsIgnoreCase("s")) {
            delivery = TernaryInfo.YES;
        } else if (inputDelivery.equalsIgnoreCase("n")) {
            delivery = TernaryInfo.NO;
        } else {
            delivery = TernaryInfo.ANY;
        }

        scriviInfo("Invio per ignorare");
        String inputBooking = leggiInput("Prenotazione online? (s/n): ");
        if (inputBooking.equalsIgnoreCase("s"))
            booking = TernaryInfo.YES;
        else if (inputDelivery.equalsIgnoreCase("n"))
            booking = TernaryInfo.NO;
        else
            booking = TernaryInfo.ANY;

        scriviInfo("Invio per ignorare");
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

        if (risultati.isEmpty()) {
            pulisciConsole();

            scriviInfo("Nessun ristorante trovato");

            attendiInputBack();
        } else {
            pulisciConsole();

            DettagliRistorantiClientePage ristorantiPageCliente = new DettagliRistorantiClientePage(scanner, risultati,
                    cliente);
            ristorantiPageCliente.start();
        }
    }

    /**
     * Visualizza i ristoranti preferiti dell'utente.
     */
    private void visualizzaPreferiti() {
        pulisciConsole();

        List<Integer> preferiti = cliente.getRistorantiIDs();

        if (preferiti == null || preferiti.isEmpty()) {
            scriviInfo("Non hai ancora aggiunto ristoranti ai preferiti.");
            attendiInputBack();
            return;
        }

        HashMap<Integer, Ristorante> ristorantiMap = RitstorantiManager.getRistoranti();
        ArrayList<Ristorante> preferiti_ristoranti = new ArrayList<>();

        for (int ristoranteID : preferiti) {
            Ristorante r = ristorantiMap.get(ristoranteID);
            if (r != null) {
                preferiti_ristoranti.add(r);
            }
        }

        if (preferiti_ristoranti.isEmpty()) {
            scriviInfo("Nessun ristorante preferito trovato.");
            attendiInputBack();
            return;
        }

        stampaMenuPreferiti();
        scriviMessaggio("Trovati " + preferiti_ristoranti.size() + " ristoranti preferiti:");

        for (int i = 0; i < preferiti_ristoranti.size(); i++) {
            scriviDettagliRistorante(i + 1, preferiti_ristoranti.get(i));
        }

        String input = leggiInput(
                "Inserisci l'indice del ristorante per visualizzare dettagli e gestire.\n0 | Torna indietro.");

        if (input.equals("0"))
            return;

        try {
            int indice = Integer.parseInt(input) - 1;
            if (indice < 0 || indice >= preferiti_ristoranti.size()) {
                scriviErrore("Indice non valido. Riprova.");
                return;
            }

            Ristorante ristoranteSelezionato = preferiti_ristoranti.get(indice);

            pulisciConsole();

            input = leggiInput(
                    "1 | Visualizza dettagli.\n2 | Visualizza recensioni.\n3 | Rimuovi dai preferiti.\n0 | Torna indietro.");

            switch (input) {
                case "0":
                    return;
                case "1":
                    pulisciConsole();

                    scriviDettagliCompletiRistorante(ristoranteSelezionato);

                    attendiInputBack();
                    break;
                case "2":
                    visualizzaRecensioniRistorante(ristoranteSelezionato);

                    attendiInputBack();
                    break;
                case "3":
                    if (conferma("Sicuro di voler rimuovere dai preferiti?")) {
                        try {
                            blCliente.rimuoviPreferito(ristoranteSelezionato.getId());
                        } catch (FileNotFoundException e) {
                            TheKnifeLogger.error(e);

                            scriviErrore("Errore salvataggio preferiti.");

                            return;
                        }

                        scriviMessaggio("Rimosso dai preferiti!");
                    }
                    attendiInputBack();
                    break;
                default:
                    break;

            }

        } catch (NumberFormatException e) {
            scriviErrore("Input non valido.");
        }
    }

    /**
     * Gestisce le operazioni relative alle recensioni.
     */
    private void gestisciRecensioni() {
        pulisciConsole();

        scriviMessaggio("==============================");
        scriviMessaggio("    GESTIONE RECENSIONI      ");
        scriviMessaggio("==============================");

        String input = leggiInput(
                "1 | Aggiungi recensione\n2 | Modifica recensione\n3 | Elimina recensione\n0 | Torna indietro.");

        switch (input) {
            case "1":
                aggiungiRecensione();
                break;
            case "2":
                modificaRecensione();
                break;
            case "3":
                eliminaRecensione();
                break;
            case "0":
                return;
            default:
                scriviErrore("Scelta non valida.");
        }
    }

    /**
     * Aggiunge una nuova recensione ad un ristorante.
     */
    private void aggiungiRecensione() {
        pulisciConsole();

        scriviMessaggio("Quale ristorante vuoi recensire?");

        HashMap<Integer, Ristorante> ristorantiMap = RitstorantiManager.getRistoranti();
        ArrayList<Ristorante> listRistoranti = new ArrayList<>(ristorantiMap.values());

        scriviMessaggio("Trovati " + listRistoranti.size() + " ristoranti:");

        for (int i = 0; i < listRistoranti.size(); i++) {
            scriviDettagliRistorante(i + 1, listRistoranti.get(i));
        }

        String input = leggiInput("Inserisci l'indice del ristorante.\n0 | Torna indietro.");

        if (input.equals("0"))
            return;

        try {
            int indice = Integer.parseInt(input) - 1;
            if (indice < 0 || indice >= listRistoranti.size()) {
                scriviErrore("Indice non valido.");
                return;
            }

            Ristorante ristoranteSelezionato = listRistoranti.get(indice);

            int stelle = -1;
            while (stelle < 1 || stelle > 5) {
                try {
                    String stelleStr = leggiInput("Valutazione (1-5): ");
                    stelle = Integer.parseInt(stelleStr);
                    if (stelle < 1 || stelle > 5) {
                        scriviErrore("La valutazione deve essere tra 1 e 5.");
                    }
                } catch (NumberFormatException e) {
                    scriviErrore("Input non valido.");
                }
            }

            String commento = leggiInput("Commento: ");

            try {
                HashMap<Integer, Recensione> recensioni = rm.getRecensioni();
                int nuovoID = recensioni.size() + 1;

                Recensione nuovaRecensione = new Recensione(nuovoID, (byte) stelle, commento, cliente.getId(),
                        ristoranteSelezionato.getId(), null);

                rm.addRecensione(nuovaRecensione);
                try {
                    rm.scriviRecensioni();
                } catch (java.io.FileNotFoundException e) {
                    scriviErrore("Errore durante il salvataggio della recensione.");
                    return;
                }

                scriviMessaggio("Recensione aggiunta con successo!");
                attendiInputBack();
            } catch (ValidationException e) {
                scriviErrore(e.getMessage());
            } catch (Exception e) {
                scriviErrore("Errore durante l'aggiunta della recensione.");
            }

        } catch (NumberFormatException e) {
            scriviErrore("Input non valido.");
        }
    }

    /**
     * Modifica una recensione esistente dell'utente.
     */
    private void modificaRecensione() {
        pulisciConsole();

        HashMap<Integer, Recensione> tuteRecensioni = rm.getRecensioni();

        // Filtra le recensioni dell'utente
        ArrayList<Recensione> mieRecensioni = new ArrayList<>();
        for (Recensione r : tuteRecensioni.values()) {
            if (r.getAutoreID() == cliente.getId()) {
                mieRecensioni.add(r);
            }
        }

        if (mieRecensioni.isEmpty()) {
            scriviInfo("Non hai ancora scritto alcuna recensione.");
            attendiInputBack();
            return;
        }

        scriviMessaggio("Le tue recensioni:");

        for (int i = 0; i < mieRecensioni.size(); i++) {
            Recensione r = mieRecensioni.get(i);
            Ristorante rist = RitstorantiManager.getRistoranti().get(r.getRistoranteID());
            System.out.println((i + 1) + ". " + rist.getNome() + " - " + r.getStelle() + " stelle");
            System.out.println("   Commento: " + r.getCommento());
        }

        String input = leggiInput("Inserisci l'indice della recensione da modificare.\n0 | Torna indietro.");

        if (input.equals("0"))
            return;

        try {
            int indice = Integer.parseInt(input) - 1;
            if (indice < 0 || indice >= mieRecensioni.size()) {
                scriviErrore("Indice non valido.");
                return;
            }

            Recensione recensioneSelezionata = mieRecensioni.get(indice);

            int stelle = -1;
            while (stelle < 1 || stelle > 5) {
                try {
                    String stelleStr = leggiInput("Nuova valutazione (1-5): ");
                    stelle = Integer.parseInt(stelleStr);
                    if (stelle < 1 || stelle > 5) {
                        scriviErrore("La valutazione deve essere tra 1 e 5.");
                    }
                } catch (NumberFormatException e) {
                    scriviErrore("Input non valido.");
                }
            }

            try {
                recensioneSelezionata.setStelle((byte) stelle);
                // Nota: Il commento non può essere modificato direttamente nel modello
                // Recensione
                // È possibile solo modificare le stelle e la risposta del ristoratore
                rm.addRecensione(recensioneSelezionata);
                try {
                    rm.scriviRecensioni();
                } catch (java.io.FileNotFoundException e) {
                    scriviErrore("Errore durante il salvataggio della recensione.");
                    return;
                }

                scriviMessaggio("Recensione modificata con successo!");
                attendiInputBack();
            } catch (ValidationException e) {
                scriviErrore(e.getMessage());
            }

        } catch (NumberFormatException e) {
            scriviErrore("Input non valido.");
        }
    }

    /**
     * Elimina una recensione dell'utente.
     */
    private void eliminaRecensione() {
        pulisciConsole();

        HashMap<Integer, Recensione> tuteRecensioni = rm.getRecensioni();

        // Filtra le recensioni dell'utente
        ArrayList<Recensione> mieRecensioni = new ArrayList<>();
        for (Recensione r : tuteRecensioni.values()) {
            if (r.getAutoreID() == cliente.getId()) {
                mieRecensioni.add(r);
            }
        }

        if (mieRecensioni.isEmpty()) {
            scriviInfo("Non hai ancora scritto alcuna recensione.");
            attendiInputBack();
            return;
        }
        while (true) {
            pulisciConsole();

            scriviMessaggio("Le tue recensioni:");

            for (int i = 0; i < mieRecensioni.size(); i++) {
                Recensione r = mieRecensioni.get(i);
                Ristorante rist = RitstorantiManager.getRistoranti().get(r.getRistoranteID());
                System.out.println((i + 1) + ". " + rist.getNome() + " - " + r.getStelle() + " stelle");
                System.out.println("   Commento: " + r.getCommento());
            }

            String input = leggiInput("Inserisci l'indice della recensione da eliminare.\n0 | Torna indietro.");

            if (input.equals("0"))
                return;

            try {
                int indice = Integer.parseInt(input) - 1;

                if (indice > 0 || indice <= mieRecensioni.size()) {

                    Recensione recensioneSelezionata = mieRecensioni.get(indice);

                    BLRecensione blRecensione = new BLRecensione(recensioneSelezionata);

                    if (blRecensione.eliminaRecensione())
                        scriviMessaggio("Recensione eliminata correttamente.");
                    else
                        scriviErrore("Eliminazione fallita.");

                    attendiInputBack();
                    return;
                }

            } catch (NumberFormatException e) {
                scriviErrore("Input non valido.");
                return;
            }
        }
    }

    /**
     * Visualizza le recensioni di un ristorante.
     */
    private void visualizzaRecensioniRistorante(Ristorante ristorante) {
        pulisciConsole();

        ArrayList<Recensione> recensioniRistorante = ristorante.getRecensioni();

        if (recensioniRistorante.isEmpty()) {
            scriviInfo("Nessuna recensione per questo ristorante.");

            attendiInputBack();
            return;
        }

        scriviMessaggio("Recensioni per " + ristorante.getNome() + ":");

        for (Recensione r : recensioniRistorante) {
            scriviMessaggio("Stelle: " + r.getStelle());
            scriviMessaggio("Commento: " + r.getCommento());

            if (!r.getRisposta().isEmpty()) {
                System.out.println("Risposta: " + r.getRisposta());
            }
        }

    }

    private void stampaMenuRicercaRistoranti() {
        pulisciConsole();

        scriviMessaggio("==============================");
        scriviMessaggio("      RICERCA RISTORANTI      ");
        scriviMessaggio("==============================");
    }

    private void stampaMenuPreferiti() {
        pulisciConsole();

        scriviMessaggio("==============================");
        scriviMessaggio("       I TUOI PREFERITI       ");
        scriviMessaggio("==============================");
    }

}
