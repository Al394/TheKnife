package theknife.utility;

import theknife.models.Ristorante;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Classe utility per lettura e scrittura ristoranti.
 */
public class RitstorantiManager extends FileManager {

    private static RitstorantiManager instance = null;
    private String RestaurantsPath;

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
     *
     * Carica tutti i ristoranti dal file CSV.
     */
    public List<Ristorante> leggiRistoranti() throws FileNotFoundException {
        List<Ristorante> ristoranti = new ArrayList<Ristorante>() {
        };

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
                if (campi.length != 10) {
                    continue;
                }

                Ristorante ristorante = parseRistorante(campi);

                ristoranti.add(ristorante);
            }

        } catch (IOException e) {
            System.out.println("Errore nella lettura dei ristoranti: " + e.getMessage());
        }

        return ristoranti;
    }

    /**
     * Salva la lista dei ristoranti su file CSV.
     *
     * @param ristoranti lista da salvare
     */
    public void scriviRistoranti(List<Ristorante> ristoranti) throws FileNotFoundException {

        File fileRistoranti = FileManager.ricavaFileDaPercorso(RestaurantsPath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileRistoranti))) {

            // Intestazione
            writer.write("nome;nazione;citta;indirizzo;latitudine;longitudine;prezzoMedio;takeAway;booking;tipoCucina");
            writer.newLine();

            for (Ristorante r : ristoranti) {
                writer.write(formatRistorante(r));
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Errore nella scrittura dei ristoranti: " + e.getMessage());
        }
    }

    private Ristorante parseRistorante(String[] c) {

        return new Ristorante(
                c[0],                         // nome
                c[1],                         // nazione
                c[2],                         // città
                c[3],                         // indirizzo
                Double.parseDouble(c[4]),     // latitudine
                Double.parseDouble(c[5]),     // longitudine
                Double.parseDouble(c[6]),     // prezzo medio
                Boolean.parseBoolean(c[7]),   // delivery
                Boolean.parseBoolean(c[8]),   // prenotazione
                c[9]                          // tipo cucina
        );
    }

    private String formatRistorante(Ristorante r) {

        return String.join(";",
                r.getNome(),
                r.getNazione(),
                r.getCitta(),
                r.getIndirizzo(),
                String.valueOf(r.getLatitudine()),
                String.valueOf(r.getLongitudine()),
                String.valueOf(r.getPrezzoMedio()),
                String.valueOf(r.isTakeAway()),
                String.valueOf(r.hasBooking()),
                r.getTipoCucina()
        );
    }
}
