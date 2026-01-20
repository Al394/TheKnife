module theknife {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires javafx.graphics;
    requires theknife;
    requires java.sql;

    opens theknife to javafx.fxml;
    exports theknife;
    exports theknife.models;
    exports theknife.exceptions;
    exports theknife.utility;
    opens theknife.models to javafx.fxml;
}