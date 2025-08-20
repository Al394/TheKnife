module theknife.theknife {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens theknife to javafx.fxml;
    exports theknife;
}