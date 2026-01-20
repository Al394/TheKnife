package theknife.utility;

import theknife.models.Admin;
import theknife.models.Cliente;
import theknife.models.Ristoratore;
import theknife.models.Utente;

import java.io.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utility per lettura e scrittura utenti.
 */
public class UtentiManager extends FileManager {

    private static UtentiManager instance = null;
    private String utentiPath;

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
     * Imposta il percorso del file utenti.
     */
    public void setUtentiPath(String utentiPath) {
        this.utentiPath = utentiPath;
    }

    /**
     * Carica tutti gli utenti dal file CSV.
     */
    public List<Utente> leggiUtenti() throws FileNotFoundException {

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
                if (campi.length != 7) {
                    continue;
                }

                Utente utente = parseUtente(campi);
                utenti.add(utente);
            }

        } catch (IOException e) {
            System.out.println("Errore nella lettura degli utenti: " + e.getMessage());
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
            writer.write("nome,cognome,username,password,dataDiNascita,domicilio,ruolo");
            writer.newLine();

            for (Utente u : utenti) {
                writer.write(formatUtente(u));
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Errore nella scrittura degli utenti: " + e.getMessage());
        }
    }

    private Utente parseUtente(String[] c) {

        String nome = c[0];
        String cognome = c[1];
        String username = c[2];
        String password = c[3];
        Date dataDiNascita = Date.valueOf(c[4]);
        String domicilio = c[5];
        Enums.Ruolo ruolo = Enums.Ruolo.valueOf(c[6]);

        switch (ruolo) {
            case ADMIN:
                return new Admin(nome, cognome, username, password, dataDiNascita, domicilio);
            case CLIENTE:
                return new Cliente(nome, cognome, username, password, dataDiNascita, domicilio);
            case RISTORATORE:
                return new Ristoratore(nome, cognome, username, password, dataDiNascita, domicilio);
            default:
                return null;
        }
    }

    private String formatUtente(Utente u) {

        return String.join(";",
                u.getNome(),
                u.getCognome(),
                u.getUsername(),
                u.getPassword(),
                u.getDataDiNascita() == null ? "" : u.getDataDiNascita().toString(),
                u.getDomicilio(),
                u.getRuolo().name()
        );
    }
}