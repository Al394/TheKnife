module theknife {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires javafx.graphics;

    opens theknife to javafx.fxml;
    exports theknife;
    exports theknife.models;
    opens theknife.models to javafx.fxml;
}