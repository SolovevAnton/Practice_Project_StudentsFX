package com.solovev.controllers;

import com.solovev.model.Car;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CarsChangeFormController implements ControllerData<Car>{
    @FXML
    public TextField brandField;
    @FXML
    public TextField powerField;
    @FXML
    public TextField yearField;
    @FXML
    public TextField studentIdField;
    @FXML
    public void declineButton(ActionEvent actionEvent) {
    }
    @FXML
    public void saveButton(ActionEvent actionEvent) {
    }

    @Override
    public void initData(Car car) throws IOException {

    }
}
