package theknife.utility;

import theknife.models.Admin;
import theknife.models.Cliente;
import theknife.models.Ristoratore;
import theknife.models.Utente;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Classe utility per lettura e scrittura utenti.
 */
public class UtentiManager extends FileManager {
    private final int NUMERO_CAMPI_UTENTE = 8;
    private final String HEADRES = "id;nome;cognome;username;password;dataDiNascita;domicilio;ruolo;ristoranti";
    private final String utentiPath = "data/users.csv";

    private static UtentiManager instance = null;

    /**
     * Costruttore privato, istanza accessibile via {@link #getInstance()}
     */
    private UtentiManager() {
    }

    /**
     * Metodo per il singleton.
     */
    public static UtentiManager getInstance() {
        if (instance == null) {
            instance = new UtentiManager();
        }
        return instance;
    }

    /**
     * Carica tutti gli utenti dal file CSV.
     *
     * @throws FileNotFoundException
     * @throws ParseException
     */
    public List<Utente> leggiUtenti() throws FileNotFoundException, ParseException {

        List<Utente> utenti = new ArrayList<>();

        File fileUtenti = FileManager.ricavaFileDaPercorso(utentiPath);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileUtenti))) {

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
                if (campi.length != NUMERO_CAMPI_UTENTE) {
                    continue;
                }

                Utente utente = parseUtente(campi);
                utenti.add(utente);
            }

        } catch (IOException e) {
            TheKnifeLogger.error(e);
        }

        return utenti;
    }

    /**
     * Salva la lista degli utenti su file CSV.
     */
    public void scriviUtenti(List<Utente> utenti) throws FileNotFoundException {

        File fileUtenti = FileManager.ricavaFileDaPercorso(utentiPath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileUtenti))) {

            // Intestazione
            writer.write(HEADRES);
            writer.newLine();

            for (Utente u : utenti) {
                writer.write(parseUtente(u));
                writer.newLine();
            }

        } catch (IOException e) {
            TheKnifeLogger.error(e);
        }
    }

    private Utente parseUtente(String[] c) throws ParseException, IllegalArgumentException {
        int id = Integer.parseInt(c[0]);
        String nome = c[1];
        String cognome = c[2];
        String username = c[3];
        String password = c[4];
        Date dataDiNascita = new SimpleDateFormat("yyyy-MM-dd").parse(c[5]);
        String domicilio = c[6];
        Enums.Ruolo ruolo = Enums.Ruolo.valueOf(c[7]);
        List<Integer> ristoranti = Arrays.stream(c[8].replaceAll("\\[|\\]", "").split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .toList();

        switch (ruolo) {
            case ADMIN:
                return new Admin(id, nome, cognome, username, password, dataDiNascita, domicilio, ristoranti);
            case CLIENTE:
                return new Cliente(id, nome, cognome, username, password, dataDiNascita, domicilio, ristoranti);
            case RISTORATORE:
                return new Ristoratore(id, nome, cognome, username, password, dataDiNascita, domicilio, ristoranti);
            default:
                throw new IllegalArgumentException("Ruolo utente non valido: " + ruolo);
        }
    }

    private String parseUtente(Utente u) {

        String[] arr = {
                String.valueOf(u.getId()),
                u.getNome(),
                u.getCognome(),
                u.getUsername(),
                u.getPassword(),
                u.getDataDiNascita().toString(),
                u.getDomicilio(),
                u.getRuolo().getRuolo(),
                u.getRistorantiIDs().toString()
        };
        return Arrays.toString(arr);
    }
}
