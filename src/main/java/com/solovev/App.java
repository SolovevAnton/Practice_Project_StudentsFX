package com.solovev;

import com.solovev.model.Student;
import com.solovev.repositories.StudentRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String mainForm = "/com/solovev/main.fxml";

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(mainForm));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}