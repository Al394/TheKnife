package theknife.utility;

import theknife.enums.Enums;
import theknife.exceptions.AuthException;
import theknife.models.Cliente;
import theknife.models.Ristoratore;
import theknife.models.Utente;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Classe utility per lettura e scrittura utenti.
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class UtentiManager extends FileManager {
    private static final int NUMERO_CAMPI_UTENTE = 11;
    private static final String HEADERS = "id;nome;cognome;username;password;dataDiNascita;nazione;citta;indirizzo;ruolo;ristoranti";
    private static final String PATH_UTENTI = "data/users.csv";

    private static UtentiManager instance = null;

    private static final SimpleDateFormat MyDateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    private static final HashMap<Integer, Utente> utentiMap = new HashMap<>();

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
     * Registra un nuovo utente.
     *
     * @param nome          Nome
     * @param cognome       Cognome
     * @param username      Username
     * @param password      Password
     * @param dataDiNascita Data di Nascita
     * @param nazione       Nazione
     * @param citta         Città
     * @param indirizzo     Indirizzo
     * @param ruolo         Ruolo
     * @throws ParseException
     * @throws FileNotFoundException
     */
    public static void registrazione(String nome, String cognome, String username, String password,
            Date dataDiNascita, String nazione, String citta, String indirizzo, Enums.Ruolo ruolo)
            throws ParseException, FileNotFoundException {

        getUtenti();

        int id = utentiMap.size() + 1;

        if (Enums.Ruolo.CLIENTE == ruolo) {
            Cliente cliente = new Cliente(id, nome, cognome, username, password, dataDiNascita, nazione, citta,
                    indirizzo, new ArrayList<Integer>());
            utentiMap.put(id, cliente);
        }

        if (Enums.Ruolo.RISTORATORE == ruolo) {
            Ristoratore ristoratore = new Ristoratore(id, nome, cognome, username, password, dataDiNascita, nazione,
                    citta, indirizzo, new ArrayList<Integer>());
            utentiMap.put(id, ristoratore);
        }

        scriviUtenti(utentiMap.values().stream().toList());
    }

    /**
     * Metodo pubblico per esporre gli utenti.
     *
     * @return HashMap ID, Utente
     */
    public static HashMap<Integer, Utente> getUtenti() {
        if (utentiMap.size() == 0) {
            try {
                leggiUtenti();
            } catch (Exception e) {
                TheKnifeLogger.error(e);
            }
        }

        return utentiMap;
    }

    /**
     * Carica tutti gli utenti dal file CSV.
     *
     * @throws FileNotFoundException
     * @throws ParseException
     */
    private static void leggiUtenti() throws FileNotFoundException, ParseException {
        File fileUtenti = FileManager.ricavaFileDaPercorso(PATH_UTENTI);

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

                Utente utente = leggiUtenteCSV(campi);

                utentiMap.put(utente.getId(), utente);
            }

        } catch (Exception e) {
            TheKnifeLogger.error(e);
        }
    }

    /**
     * Salva la lista degli utenti su file CSV.
     */
    public static void scriviUtenti(List<Utente> utenti) throws FileNotFoundException {

        File fileUtenti = FileManager.ricavaFileDaPercorso(PATH_UTENTI);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileUtenti))) {

            // Intestazione
            writer.write(HEADERS);
            writer.newLine();

            for (Utente u : utenti) {
                writer.write(scriviUtenteCSV(u));
                writer.newLine();
            }

        } catch (IOException e) {
            TheKnifeLogger.error(e);
        }
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante l'hashing", e);
        }
    }

    private static Utente leggiUtenteCSV(String[] c) throws ParseException, IllegalArgumentException {
        int id = Integer.parseInt(c[0]);
        String nome = c[1];
        String cognome = c[2];
        String username = c[3];
        String password = c[4];
        Date dataDiNascita = MyDateFormatter.parse(c[5]);
        String nazione = c[6];
        String citta = c[7];
        String indirizzo = c[8];
        Enums.Ruolo ruolo = Enums.Ruolo.valueOf(c[9]);
        List<Integer> ristoranti = Arrays.stream(c[10].replaceAll("\\[|\\]", "").split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .toList();

        switch (ruolo) {
            case CLIENTE:
                return new Cliente(id, nome, cognome, username, password, dataDiNascita, nazione, citta, indirizzo,
                        ristoranti);
            case RISTORATORE:
                return new Ristoratore(id, nome, cognome, username, password, dataDiNascita, nazione, citta, indirizzo,
                        ristoranti);
            default:
                throw new IllegalArgumentException("Ruolo utente non valido: " + ruolo);
        }
    }

    private static String scriviUtenteCSV(Utente u) {

        String[] arr = {
                String.valueOf(u.getId()),
                u.getNome(),
                u.getCognome(),
                u.getUsername(),
                hashPassword(u.getPassword()),
                MyDateFormatter.format(u.getDataDiNascita()),
                u.getNazione(),
                u.getCitta(),
                u.getIndirizzo(),
                u.getRuolo().getRuolo(),
                u.getRistorantiIDs().toString()
        };
        return String.join(";", arr);
    }

    /**
     * Effettua il login di un utente.
     *
     * @param username
     * @param password
     * @return Utente nel caso di successo.
     * @throws AuthException
     */
    public static Utente login(String username, String password) throws AuthException {
        // Carico gli utenti se non già fatto.
        getUtenti();

        for (Utente u : utentiMap.values()) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }

        throw new AuthException("Utente non trovato o credenziali non valide.");
    }

}
