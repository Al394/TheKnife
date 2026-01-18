package theknife.models;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import theknife.utility.Enums.Roles;

public class User implements Serializable {

    /**
     * Unique class identifier.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The user First Name.
     */
    public String FirstName;
    /**
     * The user Last Name.
     */
    private String LastName;
    /**
     * The user username.
     */
    private String Username;
    /**
     * The user password.
     */
    private String Password;
    /**
     * The user Birth date.
     */
    private Date BirthDate;
    /**
     * The user Residence.
     */
    private String Residence;
    /**
     * The user Role [].
     */
    private Roles Role;
    /**
     * Is user Admin.
     */
    private boolean Admin;

    public User(String firstName, String lastName, String username, String password, Date birthDate, String residence, Roles role, boolean admin) {
        FirstName = firstName;
        LastName = lastName;
        Username = username;
        Password = password;
        BirthDate = birthDate;
        Residence = residence;
        Role = role;
        Admin = admin;
    }

    public String toCSV() {
        String[] arr = {
                this.FirstName,
                this.LastName,
                this.Username,
                this.Password,
                this.BirthDate.toString(),
                this.Residence,
                this.Role.toString(),
                this.Admin ? "true" : "false"
        };
        return Arrays.toString(arr).replaceAll("\\[|\\]", "");
    }

    /**
     * Read list of users from a csv file.
     *
     * @param file The csv file with users data.
     * @return List of registered users.
     */
    public static List<User> getUsers(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                System.out.println("line => " + line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return List.of();
    }

//    public static List<User> setUsers(){
//        try (FileOutputStream fileOut = new FileOutputStream(nomeFile);
//             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
//            out.writeObject(utente);
//            System.out.println("Oggetto 'Utente' serializzato in " + nomeFile);
//        } catch (IOException i) {
//            i.printStackTrace();
//        }
//    }

}
