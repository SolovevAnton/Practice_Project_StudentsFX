package com.solovev.controllers;

import com.solovev.model.Car;
import com.solovev.repositories.CarRepository;
import com.solovev.repositories.Repository;
import com.solovev.util.FormsManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.Year;
import java.time.format.DateTimeParseException;

public class CarsChangeFormController implements ControllerData<Car>{
    @FXML
    public TextField brandField;
    @FXML
    public TextField powerField;
    @FXML
    public TextField yearField;
    @FXML
    public TextField studentIdField;
    private Car car;
    @FXML
    public void declineButton(ActionEvent actionEvent) {
        FormsManager.closeWindow(actionEvent);
    }
    @FXML
    public void saveButton(ActionEvent actionEvent) throws IOException {
        boolean updateSuccess = updateCartWithFields();
        if(updateSuccess) {
            try {
                Repository<Car> carRepo = new CarRepository();
                int carId = car.getId();


                if(carRepo.containsId(carId)){
                    carRepo.replace(car);
                } else {
                    carRepo.add(car);
                }
                FormsManager.closeWindow(actionEvent);
            } catch (IllegalArgumentException e){
                FormsManager.showAlertWithoutHeaderText("Illegal argument", e.toString(), Alert.AlertType.WARNING);
            }
        }

    }
    private boolean updateCartWithFields() {
        boolean updateSuccess = false;
        try {
            int power = Integer.parseInt(powerField.getText());
            Year year = Year.parse(yearField.getText());

            car.setBrand(brandField.getText());
            car.setPower(power);
            car.setYear(year);
            updateSuccess = true;
        } catch (NumberFormatException | DateTimeParseException e){
            FormsManager.showAlertWithoutHeaderText("Input exception", e.toString(), Alert.AlertType.WARNING);
        }
        return updateSuccess;
    }

    @Override
    public void initData(Car car) {
        this.car = car;

        brandField.setText(car.getBrand());
        powerField.setText(String.valueOf(car.getPower()));
        yearField.setText(String.valueOf(car.getYear()));
        studentIdField.setText(String.valueOf(car.getIdStudent()));
    }
}
