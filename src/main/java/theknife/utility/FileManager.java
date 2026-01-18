package theknife.utility;

// Alessio Sangiorgi 730420 VA

import theknife.models.Restaurant;
import theknife.models.User;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

/**
 * Singleton class - File Manager.
 * Read csv files to retrieve users/restaurants data.
 */
public class FileManager {

    private static FileManager instance = null;
    private Path UsersPath;
    private Path RestaurantsPath;

    /**
     * private constructor, instance accessible via
     * {@link #getInstance()}
     */
    private FileManager() {
    }

    /**
     * Method for singleton FileManager.
     *
     * @return The new or jet initialized FileManager instance.
     */
    public static FileManager getInstance() {
        if (instance == null) {
            return instance = new FileManager();
        }

        return instance;
    }

    /**
     * Sets the users file location.
     * @param usersPath The 'users.data' file location.
     */
    public void setUsersPath(String usersPath) {
        UsersPath = Paths.get(usersPath);
    }

    /**
     * Sets the restaurants Path
     * @param restaurantsPath The 'restaurants.data' file location.
     */
    public void setRestaurantsPath(String restaurantsPath) {
        RestaurantsPath = Paths.get(restaurantsPath);
    }

    public void readData(String path, Enums.FileType type) {

        // Testing
        path = "data/users.csv";
        type = Enums.FileType.USERS;
        //------------

        File file = checkPath(path);

        switch (type) {
            case USERS -> {
                List<User> users = User.getUsers(file);
            }
            case RESTAURANTS -> {
//                List<Restaurant> restaurants = getRestaurants(file);
            }
            case null, default -> {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }
    }

    /**
     * Write data to the correct file.
     *
     * @param path
     * @param type
     * @param <T>
     */
    public <T> void writeData(String path, Enums.FileType type) {

        String dataToWrite = "";
        // Testing
        path = "data/users.csv";
        type = Enums.FileType.USERS;

        User data = new User("Alessio", "Sangiorgi", "ASSAn", "1234", new Date("11/12/1994"), "Gattinara", Enums.Roles.CUSTOMER, true);
        //------------

        File file = checkPath(path);

        if (data.getClass() == User.class) {
            dataToWrite = ((User) data).toCSV();
//        } else if (data.getClass() == Restaurant.class) {
//
//        }

            try (FileWriter writer = new FileWriter(path, true)) { // 'true' per l'append, 'false' per sovrascrivere
                writer.write(System.lineSeparator() + dataToWrite);
                System.out.println("Stringa scritta su file CSV.");
            } catch (IOException e) {
                System.err.println("Errore durante la scrittura del file: " + e.getMessage());
            }

        }


    }

    private File checkPath(String path) {

        File f = new File(path);

        if (!f.exists() && !f.isDirectory()) {
            throw new UnsupportedOperationException("Unimplemented method 'CheckPath'");
        }

        return f;
    }

    public static <T> void serializeData(T data, Enums.FileType type) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/data.ser"))) {
            oos.writeObject(data);
            System.out.println("Lista di utenti serializzata con successo!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> void deserializeData(Enums.FileType type) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/data.ser"))) {

            List<T> utentiSerializzati = (List<T>) ois.readObject();

            utentiSerializzati.forEach(u -> {
                if (u instanceof User) {
                    System.out.println("Utente: " + ((User) u).FirstName );

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
