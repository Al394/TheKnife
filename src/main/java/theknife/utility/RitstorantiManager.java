package theknife.utility;

import theknife.exceptions.ValidationException;
import theknife.models.Ristorante;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * Classe utility per lettura e scrittura ristoranti.
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class RitstorantiManager extends FileManager {

    private static final int NUMERO_CAMPI_RISTORANTE = 14;
    private static final String HEADER = "id;nome;nazione;citta;indirizzo;latitudine;longitudine;prezzoMedio;delivery;booking;tipoCucina;descrizione;servizi;recensioniIDs";
    private static final String RestaurantsPath = "data/restaurants.csv";

    private static RitstorantiManager instance = null;
    private static final HashMap<Integer, Ristorante> ristorantiMap = new HashMap<>();

    /**
     * Costruttore privato, istanza accessible via {@link #getInstance()}
     */
    private RitstorantiManager() {
    }

    /**
     *
     * Metodo per il singleton.
     */
    public static RitstorantiManager getInstance() {
        if (instance == null) {
            return instance = new RitstorantiManager();
        }

        return instance;
    }

    /**
     * Metodo pubblico per esporre i ristoranti.
     *
     * @return HashMap ID, Ristorante
     */
    public static HashMap<Integer, Ristorante> getRistoranti() {
        if (ristorantiMap.size() == 0) {
            try {
                leggiRistoranti();
            } catch (FileNotFoundException e) {
                TheKnifeLogger.error(e);
            }
        }

        return ristorantiMap;
    }

    /**
     *
     * Carica tutti i ristoranti dal file CSV e li inserisce in ristorantiMap.
     */
    private static void leggiRistoranti() throws FileNotFoundException {
        ristorantiMap.clear();

        File fileRistoranti = FileManager.ricavaFileDaPercorso(RestaurantsPath);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileRistoranti))) {

            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {

                // Salta intestazione
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] campi = line.split(";");

                // Riga malformata → saltata
                if (campi.length != NUMERO_CAMPI_RISTORANTE) {
                    continue;
                }

                Ristorante ristorante = parseRistorante(campi);

                ristorantiMap.put(ristorante.getId(), ristorante);
            }

        } catch (Exception e) {
            TheKnifeLogger.error(e);
        }
    }

    /**
     * Salva i ristoranti da ristorantiMap su file CSV.
     */
    public static void scriviRistoranti() throws FileNotFoundException {

        File fileRistoranti = FileManager.ricavaFileDaPercorso(RestaurantsPath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileRistoranti))) {

            // Intestazione
            writer.write(HEADER);

            writer.newLine();

            for (Ristorante r : ristorantiMap.values()) {
                writer.write(formatRistorante(r));
                writer.newLine();
            }

        } catch (IOException e) {
            TheKnifeLogger.error(e);
        }
    }

    private static Ristorante parseRistorante(String[] c) throws NumberFormatException, ValidationException {

        return new Ristorante(
                Integer.parseInt(c[0]), // id
                c[1], // nome
                c[2], // nazione
                c[3], // città
                c[4], // indirizzo
                Double.parseDouble(c[5]), // latitudine
                Double.parseDouble(c[6]), // longitudine
                c[7], // prezzo medio
                Boolean.parseBoolean(c[8]), // delivery
                Boolean.parseBoolean(c[9]), // booking
                c[10], // tipo cucina
                c[11], // descrizione
                c[12], // servizi
                new ArrayList<>() // recensioniIDs TODO: Trasformare le recensioni da stringa a lista
        );
    }

    private static String formatRistorante(Ristorante r) {

        return String.join(";",
                String.valueOf(r.getId()),
                r.getNome(),
                r.getNazione(),
                r.getCitta(),
                r.getIndirizzo(),
                String.valueOf(r.getLatitudine()),
                String.valueOf(r.getLongitudine()),
                String.valueOf(r.getPrezzoMedio()),
                String.valueOf(r.isTakeAway()),
                String.valueOf(r.hasBooking()),
                r.getTipoCucina(),
                r.getDescrizione(),
                r.getServizi(),
                String.valueOf(r.getRecensioniIDs()));
    }
}
