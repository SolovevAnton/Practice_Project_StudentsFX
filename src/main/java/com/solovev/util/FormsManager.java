package com.solovev.util;

import com.solovev.model.Student;
import javafx.scene.control.Alert;

import java.io.IOException;

/**
 * Class to open forms with
 */
public class FormsManager extends WindowManager {
    private interface RunnableWithThrows {
        void run() throws IOException;
    }

    public static void openMainForm() {
        ioExceptionHandler(() -> openWindow("/com/solovev/main.fxml", "Front for Student Servlet", null));
    }

    public static void openCarsForm(Student student) {
        ioExceptionHandler(() -> openWindowAndWait("/com/solovev/carsTable.fxml", "Student's cars", student));
    }

    /**
     * Method to handle exceptions, so they can be seen in app
     *
     * @param method to run , should throw IO exception
     */
    private static void ioExceptionHandler(RunnableWithThrows method) {
        try {
            method.run();
        } catch (IOException e) {
            FormsManager.showAlertWithoutHeaderText("IOException Occurred!", e.toString(), Alert.AlertType.ERROR);
            throw new RuntimeException(e);
        }
    }
}