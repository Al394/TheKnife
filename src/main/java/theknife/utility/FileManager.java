package theknife.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 *
 * Classe Singleton FileManager, rappresenta l'oggetto FileManager
 * Utilizzato per la gestione dei file
 * Verifica l'esistenza altrimenti li crea.
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class FileManager {

    /**
     * Ricavo il file dal percorso e lo creo.
     *
     * @param path file path
     * @return File da leggere/scriver
     * @throws IOException
     */
    public static File ricavaFileDaPercorso(Path path) throws IOException {

        File f = path.toFile();

        if (!f.exists())
            f.createNewFile();

        return f;
    }
}
