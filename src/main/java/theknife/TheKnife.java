package theknife;

import theknife.navigation.HomePage;
import theknife.utility.RecensioniManager;
import theknife.utility.TheKnifeLogger;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Main class, punto di partenza dell'applicazione.
 *
 * @author Alessio Sangiorgi 730420 VA
 */
public class TheKnife {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        HomePage homePage = new HomePage(scanner);
        try {
            homePage.start();
        } catch (Exception e) {
            TheKnifeLogger.error(e);
        } finally {
            scanner.close();
        }
    }
}
