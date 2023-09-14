package com.solovev.controllers;

import com.solovev.model.Student;
import com.solovev.repositories.StudentRepository;
import com.solovev.util.WindowManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    public TableView<Student> studentsTable;
    @FXML
    public TableColumn<Student, Integer> columnId;
    @FXML
    public TableColumn<Student, String> columnName;
    @FXML
    public TableColumn<Student, Integer> columnAge;
    @FXML
    public TableColumn<Student, Integer> columnNum;
    @FXML
    public TableColumn<Student, Double> columnSalary;

    public void initialize() throws IOException {
        //initialize columns
        initializeTableColumns();

        //add items
        List<Student> students = new ArrayList<>(new StudentRepository().takeData());
        studentsTable.setItems(FXCollections.observableList(students));

        //initialize muse click action
        initializeMouseDoubleClickActionOnTable();
    }

    /**
     * Initializes table columns
     */
    private void initializeTableColumns() {
        //initializes columns
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        columnNum.setCellValueFactory(new PropertyValueFactory<>("num"));
        columnSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    /**
     * Sets action of the double click on the tables value
     */
    private void initializeMouseDoubleClickActionOnTable() {
        studentsTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                openCarsWindow(studentsTable.getSelectionModel().getSelectedItem());
            }
        });
    }

    /**
     * opens window with student cars
     *
     * @param student to open cars window for
     */
    private void openCarsWindow(Student student) {
        try {
            WindowManager.openWindowAndWait("/com/solovev/CarsTable.fxml", "Student's cars", student);
        } catch (IOException e) {
            WindowManager.showAlertWithoutHeaderText("IO Exception occurred", e.toString(), Alert.AlertType.ERROR);
            throw new RuntimeException(e);
        }
    }

}
