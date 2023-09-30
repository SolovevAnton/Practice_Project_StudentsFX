package com.solovev.controllers;

import com.solovev.model.Student;
import com.solovev.repositories.StudentRepository;
import com.solovev.util.WindowManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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

        addDeleteButtonColumn();
    }
    private void addDeleteButtonColumn(){
        TableColumn<Student, Button> buttonTableColumn = new TableColumn<>("Delete");
        studentsTable.getColumns().add(buttonTableColumn);
        buttonTableColumn.setCellFactory(tc -> new TableCell<>(){
                    final Button btn = deleteButtonFactory();
                    @Override
                    public void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                }
        );
    }

    private Button deleteButtonFactory(){
        Button deleteButton = new Button("Delete");
        deleteButton.setAlignment(Pos.CENTER);
        deleteButton.getStyleClass().add("danger");
        //todo add func
        deleteButton.setOnAction(event -> System.out.println("deleted"));
        return deleteButton;
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

    @FXML
    public void addStudentButton(ActionEvent actionEvent) {
    }
}
