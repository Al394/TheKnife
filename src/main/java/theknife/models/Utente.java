package theknife.models;

import java.io.*;
import java.util.Arrays;
import java.util.Date;

import theknife.utility.Enums.Ruolo;
/**
 *
 * Classe astratta Utente, rappresenta l'oggetto Utente
 * @author Alessio Sangiorgi 730420 VA
 */
abstract public class Utente implements Serializable {

    /**
     * Unique class identifier.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Nome utente.
     */
    protected String nome;
    /**
     * Cognome utente.
     */
    protected String cognome;
    /**
     * Username utente.
     */
    protected String username;
    /**
     * Password utente.
     */
    protected String password;
    /**
     * Data di nascita.
     */
    protected Date dataDiNascita;

    /**
     * Domicilio utente.
     */
    protected String domicilio;
    /**
     * Ruolo utente.
     */
    protected Ruolo ruolo;

    /** Getters **/
    public Ruolo getRuolo() {
        return ruolo;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public Date getDataDiNascita() {
        return dataDiNascita;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getCognome() {
        return cognome;
    }

    public String getNome() {
        return nome;
    }

    /**
     * Costruttore vuoto.
     */
    public Utente() {
    }

    /**
     *
     * Costruttore facilitato.
     * @param nome          Nome
     * @param cognome       Cognome
     * @param username      Username
     * @param password      Password
     * @param dataDiNascita Data di Nascita
     * @param domicilio     Domicilio
     * @param role          Ruolo
     */
    public Utente(String nome, String cognome, String username, String password, Date dataDiNascita, String domicilio, Ruolo role) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
        this.dataDiNascita = dataDiNascita;
        this.domicilio = domicilio;
        this.ruolo = role;
    }


    public String toCSV() {
        String[] arr = {
                this.nome,
                this.cognome,
                this.username,
                this.password,
                this.dataDiNascita.toString(),
                this.domicilio,
                this.ruolo.toString(),
        };
        return Arrays.toString(arr).replaceAll("\\[|\\]", "");
    }

//    /**
//     * Read list of users from a csv file.
//     *
//     * @param file The csv file with users data.
//     * @return List of registered users.
//     */
//    public static List<Utente> getUsers(File file) {
//        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//            String line;
//            boolean firstLine = true;
//
//            while ((line = br.readLine()) != null) {
//                if (firstLine) {
//                    firstLine = false;
//                    continue;
//                }
//                System.out.println("line => " + line);
//            }
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        return List.of();
//    }
}
