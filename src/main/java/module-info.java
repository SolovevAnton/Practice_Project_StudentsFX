module com.solovev {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    opens com.solovev to javafx.fxml;
    opens com.solovev.model to com.fasterxml.jackson.databind;
    opens com.solovev.repositories to com.fasterxml.jackson.databind;
    opens com.solovev.dto to com.fasterxml.jackson.databind;

    exports com.solovev.model;
    exports com.solovev;
    exports com.solovev.controllers;
}