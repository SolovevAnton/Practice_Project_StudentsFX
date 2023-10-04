package com.solovev;

import com.solovev.util.FormsManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        FormsManager.openMainForm();
    }

    public static void main(String[] args) {
        launch();
    }
}