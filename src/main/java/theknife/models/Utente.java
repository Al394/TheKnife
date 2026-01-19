package theknife.models;

import java.io.*;
import java.util.Arrays;
import java.util.Date;

import theknife.utility.Enums.Ruoli;

abstract public class Utente implements Serializable {

    /**
     * Unique class identifier.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Nome utente.
     */
    protected String Nome;
    /**
     * Cognome utente.
     */
    protected String Cognome;
    /**
     * Username utente.
     */
    protected String Username;
    /**
     * Password utente.
     */
    protected String Password;
    /**
     * Data di nascita.
     */
    protected Date DataDiNascita;
    /**
     * Domicilio utente.
     */
    protected String Domicilio;
    /**
     * Ruolo utente.
     */
    protected Ruoli Ruolo;
    /**
     * L'utente è Admin.
     */
    protected boolean Admin;

    /**
     * Costruttore vuoto.
     */
    public Utente() {}

    /**
     *
     * @param nome          Nome
     * @param cognome       Cognome
     * @param username      Username
     * @param password      Password
     * @param dataDiNascita Data di Nascita
     * @param domicilio     Domicilio
     * @param role          Ruolo
     * @param admin         L'utente è admin
     */
    public Utente(String nome, String cognome, String username, String password, Date dataDiNascita, String domicilio, Ruoli role, boolean admin) {
        Nome = nome;
        Cognome = cognome;
        Username = username;
        Password = password;
        DataDiNascita = dataDiNascita;
        Domicilio = domicilio;
        Admin = admin;
    }

    public String toCSV() {
        String[] arr = {
                this.Nome,
                this.Cognome,
                this.Username,
                this.Password,
                this.DataDiNascita.toString(),
                this.Domicilio,
                this.Ruolo.toString(),
                this.Admin ? "true" : "false"
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
