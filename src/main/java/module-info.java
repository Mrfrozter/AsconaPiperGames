module hill.ascona.asconapipergames {
    requires javafx.controls;
    requires javafx.fxml;


    opens hill.ascona.asconapipergames to javafx.fxml;
    exports hill.ascona.asconapipergames;
}