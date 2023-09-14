package com.solovev.controllers;

import com.solovev.model.Car;
import com.solovev.model.Student;
import com.solovev.repositories.CarRepository;
import com.solovev.repositories.Repository;
import com.solovev.util.WindowManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.Year;
import java.util.List;

public class CarsTableController implements ControllerData<Student>{
    @FXML
    public TableColumn<Car,Integer> columnID;
    @FXML
    public TableColumn<Car,String> columnBrand;
    @FXML
    public TableColumn<Car,Integer> columnPower;
    @FXML
    public TableColumn<Car, Year> columnYear;
    @FXML
    public Label labelID;
    @FXML
    public Label labelName;
    @FXML
    public Label labelNum;
    @FXML
    public TableView<Car> tableCars;
    private Student student;

    @Override
    public void initData(Student student)  {
        this.student = student;
        
        initializeLabels();
        initializeTable();
        //set data
        try {
            tableCars.setItems(FXCollections.observableList(getCars()));
        } catch (IOException e) {
            WindowManager.showAlertWithoutHeaderText("IO Exception occurred",e.toString(), Alert.AlertType.ERROR);
            throw new RuntimeException(e);
        }
    }
    /**
     * Sets value for students info labels
     */
    private void initializeLabels(){
        labelID.setText(String.valueOf(student.getId()));
        labelName.setText(String.valueOf(student.getName()));
        labelNum.setText(String.valueOf(student.getNum()));
    }
    /**
     * Initializes columns' factories
     */
    private void initializeTable(){
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        columnYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        columnPower.setCellValueFactory(new PropertyValueFactory<>("power"));
    }

    /**
     * Gets all cars that have student ID of the given student
     * @return List of cars or empty if none
     */
    private List<Car> getCars() throws IOException {
        //new repo is created since some info can be changed
        Repository<Car> repo = new CarRepository();
        return repo
                .takeData()
                .stream()
                .filter(car -> car.getIdStudent() == student.getId())
                .toList();
    } 


    
}
