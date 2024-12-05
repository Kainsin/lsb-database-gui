module org.kainsin.lsbdatabasegui {
    requires com.fasterxml.classmate;
    requires com.zaxxer.hikari;
    requires jakarta.persistence;
    requires jakarta.transaction;
    requires jakarta.xml.bind;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires net.bytebuddy;
    requires org.hibernate.commons.annotations;
    requires org.hibernate.orm.core;
    requires org.jboss.logging;
    requires org.slf4j;
    requires static lombok;
    requires org.apache.commons.lang3;
    requires jakarta.validation;

    opens org.kainsin.lsbdatabasegui to
            javafx.fxml;

    exports org.kainsin.lsbdatabasegui;
    exports org.kainsin.lsbdatabasegui.items;

    opens org.kainsin.lsbdatabasegui.items to
            javafx.fxml;

    exports org.kainsin.lsbdatabasegui.entities.items;

    opens org.kainsin.lsbdatabasegui.entities.items to
            javafx.fxml,
            org.hibernate.orm.core;
}
