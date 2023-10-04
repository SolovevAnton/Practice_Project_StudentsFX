package com.solovev.controllers;

import com.solovev.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class StudentChangeFormController implements ControllerRetrieveData<Boolean>, ControllerData<Student>{
    private Boolean saveData = false;
    @FXML
    public TextField nameField;
    @FXML
    public TextField numField;
    @FXML
    public TextField salaryField;
    @FXML
    public void saveButton(ActionEvent actionEvent) {
    }
    @FXML
    public void declineButton(ActionEvent actionEvent) {
    }

    @Override
    public void initData(Student value) {

    }

    @Override
    public Boolean retrieveData() {
        return saveData;
    }
}
