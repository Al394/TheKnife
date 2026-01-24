package theknife;

import theknife.models.Coordinate;
import theknife.utility.GeoLocationManager;

import java.nio.file.Paths;

public class TheKnife {
    public static void main(String[] args) {
        String path = System.getProperty("user.dir");
        String path2 = Paths.get("").toAbsolutePath().toString();
        double res = 0.0;

        System.out.println("Path 1: " + path);

        System.out.println("Path 2: " + path2);
        // Application.launch(MainStage.class, args);

        try {
            Coordinate milano = new Coordinate(45.465454, 9.186516);
            Coordinate roma = new Coordinate(41.8933203, 12.4829321);
            res = GeoLocationManager.calcoloDistanza(milano, roma);

        } catch (Exception e) {
        }

        System.out.println("Distanza: " + res);

    }
}
