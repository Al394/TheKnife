package theknife.utility;

// Alessio Sangiorgi 730420 VA

import theknife.models.Restaurant;
import theknife.models.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Singleton class - File Manager.
 * Read csv files to retrieve users/restaurants data.
 */
public class FileManager {

    private static FileManager instance = null;

    /**
     * private constructor, instance accessible via
     * {@link #getInstance()}
     */
    private FileManager() {
    }

    /**
     * Method for singletone FileManager.
     *
     * @return The new or jet initialized FileManager instance.
     */
    public static FileManager getInstance() {
        if (instance == null) {
            return instance = new FileManager();
        }

        return instance;
    }

    public void readData(String path, Enums.FileType type) {

        // Testing
        path = "data/users.csv";
        type = Enums.FileType.USERS;
        //------------

        File file = checkPath(path);

        switch (type) {
            case USERS -> {
//                List<User> users = getUsers(file);
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
     * @param type //     * @param data
     * @param <T>
     */
    public <T> void writeData(String path, Enums.FileType type) {

        String dataToWrite = "";
        // Testing
        path = "data/users.csv";
        type = Enums.FileType.USERS;

        User data = new User("Alessio",
                "Sangiorgi",
                "ASSAn",
                "1234",
                new Date("11/12/1994"),
                "Gattinara",
                Enums.Roles.CUSTOMER,
                true);
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

    private  static void getUsers(File f) {

    }

}
