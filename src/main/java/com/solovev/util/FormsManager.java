package com.solovev.util;

import com.solovev.model.Student;
import javafx.scene.control.Alert;

import java.io.IOException;

/**
 * Class to open forms with
 */
public class FormsManager extends WindowManager {
    @FunctionalInterface
    private interface RunnableWithIoExpThrows {
        void run() throws IOException;
    }

    @FunctionalInterface
    private interface SupplierWithIoExpThrows<T> {
        T get() throws IOException;
    }

    public static void openMainForm() {
        ioExceptionHandler(() -> openWindow("/com/solovev/main.fxml", "Front for Student Servlet", null));
    }

    public static void openCarsForm(Student student) {
        ioExceptionHandler(() -> openWindowAndWait("/com/solovev/carsTable.fxml", "Student's cars", student));
    }

    public static boolean openStudentChangeForm(Student student) {
        SupplierWithIoExpThrows<Boolean> openStudentChangeForm = () -> openWindowAndWaitWithRetrieveData("/com/solovev/studentChangeFrom.fxml", "Modify student", student);
        //returns if changes should be saved or not
        boolean result = false;
        try {
            result = ioExceptionHandler(openStudentChangeForm);
        } catch (IllegalArgumentException e) {
            handleIllegalArgumentException(e);
        }
        return result;
    }

    /**
     * Method to handle exceptions, so they can be seen in app
     *
     * @param method to run , should throw IO exception
     */
    private static void ioExceptionHandler(RunnableWithIoExpThrows method) {
        try {
            method.run();
        } catch (IOException e) {
            handleIOException(e);
        }
    }

    private static <T> T ioExceptionHandler(SupplierWithIoExpThrows<T> method) {
        T result = null;
        try {
            result = method.get();
        } catch (IOException e) {
            handleIOException(e);
        }
        return result;
    }

    private static void handleIOException(IOException e) {
        showAlertWithoutHeaderText("IOException Occurred!", e.toString(), Alert.AlertType.ERROR);
        throw new RuntimeException(e);
    }

    private static void handleIllegalArgumentException(IllegalArgumentException e) {
        showAlertWithoutHeaderText("Illegal argument", e.toString(), Alert.AlertType.WARNING);
    }
}
