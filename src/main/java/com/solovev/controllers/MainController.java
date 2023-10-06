package com.solovev.controllers;

import com.solovev.model.Student;
import com.solovev.repositories.StudentRepository;
import com.solovev.util.FormsManager;
import com.solovev.util.TableColumnBuilder;
import com.solovev.util.WindowManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.beans.EventHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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
    private StudentRepository studentRepo;

    public void initialize() throws IOException {
        //initialize columns
        initializeTableColumns();

        //add items
        reloadTableValues();

        //initialize muse click action
        initializeMouseDoubleClickActionOnTable();
    }

    private void reloadTableValues() throws IOException {
        studentRepo = new StudentRepository();
        List<Student> students = new ArrayList<>(studentRepo.takeData());
        studentsTable.setItems(FXCollections.observableList(students));
        studentsTable.refresh();
    }

    /**
     * Initializes table columns
     */
    private void initializeTableColumns() throws IOException {
        //initializes columns
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        columnNum.setCellValueFactory(new PropertyValueFactory<>("num"));
        columnSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        createAndAddButtonsColumn();
    }

    private void createAndAddButtonsColumn() throws IOException {
        TableColumnBuilder<Student> tableColumnBuilder = new TableColumnBuilder<>();

        tableColumnBuilder.addButtonToColumn(this::modifyButtonFactory);
        tableColumnBuilder.addButtonToColumn(this::deleteButtonFactory);

        //crete header
        tableColumnBuilder.addHeader(addButtonFactory());

        studentsTable.getColumns().add(tableColumnBuilder.getColumnWithButtons());
    }

    private Button addButtonFactory() throws IOException {
        Button addButton = new Button("+ Add");
        addButton.getStyleClass().add("success");
        addButton.setAlignment(Pos.CENTER_RIGHT);

        addButton.setOnAction((event) -> studentUpdateAction(new Student()));
        return addButton;
    }

    private Button modifyButtonFactory() {
        Button modifyButton = getButtonTemplate("Modify");
        modifyButton.getStyleClass().add("accent");

        modifyButton.setOnAction((event) -> {
            studentUpdateAction(getSelectedItem());
        });
        return modifyButton;
    }

    private void studentUpdateAction(Student studentToSaveOrUpdate) {
        FormsManager.openStudentChangeForm(studentToSaveOrUpdate);
        try {
            reloadTableValues();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Button deleteButtonFactory() {
        Button deleteButton = getButtonTemplate("Delete");
        deleteButton.getStyleClass().add("danger");
        //todo add func
        deleteButton.setOnAction(event -> System.out.println("deleted"));
        return deleteButton;
    }

    private Button getButtonTemplate(String buttonName) {
        Button button = new Button(buttonName);
        button.setAlignment(Pos.CENTER);
        button.getStyleClass().addAll("button-outlined", "small");

        return button;
    }


    /**
     * Sets action of the double click on the tables value
     */
    private void initializeMouseDoubleClickActionOnTable() {
        studentsTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                FormsManager.openCarsForm(getSelectedItem());
            }
        });
    }

    private Student getSelectedItem() {
        return studentsTable.getSelectionModel().getSelectedItem();
    }

}
