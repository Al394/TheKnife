package theknife.utility;

import theknife.enums.Enums.LogLevel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logger per il progetto TheKnife.
 * Scrive i log in append su file data/Log.txt con data, ora, livello e
 * messaggio formattati.
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class TheKnifeLogger {
    /**
     * Percorso del file di log.
     */
    private static final Path LOG_FILE_PATH = Paths.get("data", "log.csv");
    /**
     * Formattatore data.
     */
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Registra un messaggio DEBUG.
     *
     * @param message il messaggio da registrare
     */
    public static void debug(String message) {
        writeLog(LogLevel.DEBUG, message);
    }

    /**
     * Registra un messaggio INFO.
     *
     * @param message il messaggio da registrare
     */
    public static void info(String message) {
        writeLog(LogLevel.INFO, message);
    }

    /**
     * Registra un messaggio WARNING.
     *
     * @param message il messaggio da registrare
     */
    public static void warning(String message) {
        writeLog(LogLevel.WARNING, message);
    }

    /**
     * Registra un messaggio ERROR.
     *
     * @param message il messaggio da registrare
     */
    public static void error(String message) {
        writeLog(LogLevel.ERROR, message);
    }

    /**
     * Registra un'eccezione come ERROR.
     *
     * @param exception l'eccezione da registrare
     */
    public static void error(Exception exception) {
        String errorMessage = exception.getClass().getSimpleName() + ": " + exception.getMessage();
        writeLog(LogLevel.ERROR, errorMessage);
    }

    /**
     * Registra un'eccezione come ERROR.
     *
     * @param message   il messaggio aggiuntivo da registrare
     * @param exception l'eccezione da registrare
     */
    public static void error(String message, Exception exception) {
        String errorMessage = message + " | " + exception.getClass().getSimpleName() + ": " + exception.getMessage();
        writeLog(LogLevel.ERROR, errorMessage);
    }

    /**
     * Registra un messaggio con livello specificato.
     *
     * @param level   il livello di log
     * @param message il messaggio da registrare
     */
    public static void log(LogLevel level, String message) {
        writeLog(level, message);
    }

    /**
     * Scrive il messaggio nel file di log con data, ora e livello.
     *
     * @param level   il livello di log
     * @param message il messaggio da scrivere
     */
    private static void writeLog(LogLevel level, String message) {
        try {
            File logFile = LOG_FILE_PATH.toFile();

            // Crea il file se non esiste
            if (!logFile.exists()) {

                logFile.getParentFile().mkdirs();

                logFile.createNewFile();
            }

            // Scrivi in append
            try (FileWriter fw = new FileWriter(logFile, true);
                 BufferedWriter bw = new BufferedWriter(fw)) {

                String timestamp = LocalDateTime.now().format(dateTimeFormatter);
                String logEntry = "[" + timestamp + "] [" + level.getLevel() + "] " + message;

                bw.write(logEntry);
                bw.newLine();

            }
        } catch (IOException e) {
            System.err.println("Errore nella scrittura del log: " + e.getMessage());
        }
    }

}
