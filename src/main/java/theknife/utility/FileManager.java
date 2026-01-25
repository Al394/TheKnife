package theknife.utility;

// Alessio Sangiorgi 730420 VA

import java.io.*;

/**
 *
 * Classe Singleton FileManager, rappresenta l'oggetto FileManager
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class FileManager {

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
