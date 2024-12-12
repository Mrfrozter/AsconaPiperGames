module hill.ascona.asconapipergames {
    requires javafx.controls;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens hill.ascona.asconapipergames to org.hibernate.orm.core;
    exports hill.ascona.asconapipergames;
}