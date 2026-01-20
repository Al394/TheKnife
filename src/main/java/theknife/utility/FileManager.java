package theknife.utility;

// Alessio Sangiorgi 730420 VA

import theknife.models.Utente;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * Classe Singleton FileManager, rappresenta l'oggetto FileManager
 * @author Alessio Sangiorgi 730420 VA
 */
public class FileManager {

    public void readData(String path, Enums.TipoFile type) throws FileNotFoundException {

        // Testing
        path = "data/users.csv";
        type = Enums.TipoFile.UTENTI;
        //------------

        File file = ricavaFileDaPercorso(path);

//        switch (type) {
//            case UTENTI -> {
//                List<Utente> utentes = Utente.getUsers(file);
//            }
//            case RISTORANTI -> {
////                List<Restaurant> restaurants = getRestaurants(file);
//            }
//            case null, default -> {
//                throw new UnsupportedOperationException("Not supported yet.");
//            }
//        }
    }

    /**
     * Write data to the correct file.
     *
     * @param path
     * @param type
     * @param <T>
     */
    public <T> void writeData(String path, Enums.TipoFile type) {

        String dataToWrite = "";
        // Testing
        path = "data/users.csv";
        type = Enums.TipoFile.UTENTI;

//        Utente data = new Utente("Alessio", "Sangiorgi", "ASSAn", "1234", new Date("11/12/1994"), "Gattinara", Enums.Ruoli.CLIENTE, true);
        //------------

//        File file = getFileFromPath(path);

//        if (data.getClass() == Utente.class) {
//            dataToWrite = ((Utente) data).toCSV();
////        } else if (data.getClass() == Restaurant.class) {
////
////        }
//
//            try (FileWriter writer = new FileWriter(path, true)) { // 'true' per l'append, 'false' per sovrascrivere
//                writer.write(System.lineSeparator() + dataToWrite);
//                System.out.println("Stringa scritta su file CSV.");
//            } catch (IOException e) {
//                System.err.println("Errore durante la scrittura del file: " + e.getMessage());
//            }
//
//        }
    }

    public static File ricavaFileDaPercorso(String path) throws FileNotFoundException {

        File f = new File(path);

        if (!f.exists()) {
            throw new FileNotFoundException("File non trovato in questo percorso: " + path);
        }

        if (!f.isFile()) {
            throw new FileNotFoundException("Il percorso è una cartella: " + path);
        }

        return f;
    }
}
