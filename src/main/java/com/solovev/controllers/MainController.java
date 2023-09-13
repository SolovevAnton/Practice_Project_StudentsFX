package com.solovev.controllers;

import com.solovev.model.Student;
import com.solovev.repositories.StudentRepository;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
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
        initializeTable();

        List<Student> students = new ArrayList<>(new StudentRepository().takeData());
        studentsTable.setItems(FXCollections.observableList(students));
    }

    /**
     * Initializes table columns
     */
    private void initializeTable() {
        //initializes columns
        columnId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

//        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
//        columnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
//        columnNum.setCellValueFactory(new PropertyValueFactory<>("num"));
//        columnSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

}
