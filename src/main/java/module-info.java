module hill.ascona.asconapipergames {
    requires javafx.controls;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens hill.ascona.asconapipergames.entities to org.hibernate.orm.core, javafx.base;
    exports hill.ascona.asconapipergames;
}