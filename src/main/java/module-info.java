module hill.ascona.asconapipergames {
    requires javafx.controls;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.desktop;

    opens hill.ascona.asconapipergames.entities to org.hibernate.orm.core;
    exports hill.ascona.asconapipergames;
}