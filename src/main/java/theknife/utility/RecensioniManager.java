package theknife.utility;

import theknife.exceptions.ValidationException;
import theknife.models.Recensione;

import java.io.*;
import java.text.ParseException;
import java.util.HashMap;

/**
 * Classe utility per lettura e scrittura recensioni.
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class RecensioniManager extends FileManager {

  /**
   * Numero di campi per ogni recensione nel file CSV.
   */
  private final int NUMERO_CAMPI_RECENSIONE = 6;
  /**
   * Header csv.
   */
  private final String HEADER = "id;stelle;commento;autoreID;ristoranteID;risposta";
  /**
   * Percorso del file recensioni.
   */
  private final String recensioniPath = "data/recensioni.csv";
  /**
   * Istanza singleton.
   */
  private static RecensioniManager instance = null;
  /**
   * Mappa delle recensioni, chiave: ID recensione, valore: oggetto Recensione.
   */
  private static final HashMap<Integer, Recensione> recensioniMap = new HashMap<>();

  /**
   * Costruttore privato, istanza accessibile via {@link #getInstance()}
   */
  private RecensioniManager() {
  }

  /**
   * Metodo per il singleton.
   */
  public static RecensioniManager getInstance() {
    if (instance == null) {
      instance = new RecensioniManager();
    }
    return instance;
  }

  /**
   * Carica tutte le recensioni dal file CSV e le inserisce in recensioniMap.
   */
  public void leggiRecensioni() throws FileNotFoundException {
    recensioniMap.clear();

    File fileRecensioni = FileManager.ricavaFileDaPercorso(recensioniPath);

    try (BufferedReader reader = new BufferedReader(new FileReader(fileRecensioni))) {

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
        if (campi.length != NUMERO_CAMPI_RECENSIONE) {
          TheKnifeLogger.error("Recensioni: Numero campi insufficienti");
        }

        Recensione recensione = parseRecensione(campi);
        recensioniMap.put(recensione.getId(), recensione);
      }

    } catch (Exception e) {
      TheKnifeLogger.error(e);
    }
  }

  /**
   * Salva le recensioni da recensioniMap su file CSV.
   */
  public void scriviRecensioni() throws FileNotFoundException {

    File fileRecensioni = FileManager.ricavaFileDaPercorso(recensioniPath);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileRecensioni))) {

      // Intestazione
      writer.write(HEADER);
      writer.newLine();

      for (Recensione r : recensioniMap.values()) {
        writer.write(formatRecensione(r));
        writer.newLine();
      }

    } catch (IOException e) {
      TheKnifeLogger.error(e);
    }
  }

  /**
   * Ottiene una recensione dalla mappa per ID.
   */
  public Recensione getRecensione(int id) {
    return recensioniMap.get(id);
  }

  /**
   * Ottiene tutte le recensioni.
   */
  public HashMap<Integer, Recensione> getRecensioni() {
    try {
      leggiRecensioni();
    } catch (FileNotFoundException e) {
      TheKnifeLogger.error(e);
    }

    return recensioniMap;
  }

  /**
   * Aggiunge una nuova recensione alla mappa.
   */
  public void addRecensione(Recensione recensione) {
    // Sostituisco la vecchia recensione.
    recensioniMap.put(recensione.getId(), recensione);

    try {
      scriviRecensioni();
    } catch (java.io.FileNotFoundException e) {
      TheKnifeLogger.error(e);
    }
  }

  /**
   * Rimuove una recensione dalla mappa per ID.
   */
  public void removeRecensione(int id) {
    recensioniMap.remove(id);
  }

  private Recensione parseRecensione(String[] c)
      throws NumberFormatException, ValidationException, ParseException {

    int id = Integer.parseInt(c[0]);
    byte stelle = Byte.parseByte(c[1]);
    String commento = c[2];
    int autoreID = Integer.parseInt(c[3]);
    int ristoranteID = Integer.parseInt(c[4]);
    String risposta = c[5].isEmpty() ? "" : c[5];

    Recensione recensione = new Recensione(id, stelle, commento, autoreID, ristoranteID, risposta);

    return recensione;
  }

  private String formatRecensione(Recensione r) {
    return String.join(";",
        String.valueOf(r.getId()),
        String.valueOf(r.getStelle()),
        r.getCommento(),
        String.valueOf(r.getAutoreID()),
        String.valueOf(r.getRistoranteID()),
        r.getRisposta());
  }
}
