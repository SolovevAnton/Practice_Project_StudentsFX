module com.solovev {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.desktop;

    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    opens com.solovev to javafx.fxml;
    opens com.solovev.controllers to javafx.fxml;
    opens com.solovev.model;
    opens com.solovev.repositories to com.fasterxml.jackson.databind;
    opens com.solovev.dto to com.fasterxml.jackson.databind;

    exports com.solovev.model;
    exports com.solovev;
    exports com.solovev.controllers;
}