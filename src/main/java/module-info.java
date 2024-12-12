module hill.ascona.asconapipergames {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;


    opens hill.ascona.asconapipergames to javafx.fxml;
    exports hill.ascona.asconapipergames;
}