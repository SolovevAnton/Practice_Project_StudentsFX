package com.solovev.controllers;

import com.solovev.model.Car;
import com.solovev.model.Student;
import com.solovev.repositories.CarRepository;
import com.solovev.repositories.Repository;
import com.solovev.util.FormsManager;
import com.solovev.util.TableColumnBuilder;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.Year;
import java.util.List;
import java.util.Optional;

public class CarsTableController implements ControllerData<Student> {
    @FXML
    public TableColumn<Car, Integer> columnID;
    @FXML
    public TableColumn<Car, String> columnBrand;
    @FXML
    public TableColumn<Car, Integer> columnPower;
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
    private final Repository<Car> carRepo = new CarRepository();

    @Override
    public void initData(Student student) throws IOException {
        this.student = student;

        initializeLabels();
        initializeTable();
        //set data
        reloadTableValues();
    }

    /**
     * Sets value for students info labels
     */
    private void initializeLabels() {
        labelID.setText(String.valueOf(student.getId()));
        labelName.setText(String.valueOf(student.getName()));
        labelNum.setText(String.valueOf(student.getNum()));
    }

    /**
     * Initializes columns' factories
     */
    private void initializeTable() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        columnYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        columnPower.setCellValueFactory(new PropertyValueFactory<>("power"));

        createAndAddButtonsColumn();
    }

    private void createAndAddButtonsColumn() {
        TableColumnBuilder<Car> tableColumnBuilder = new TableColumnBuilder<>();

        tableColumnBuilder.addHeader(addButtonFactory());

        tableColumnBuilder.addButtonToColumn(this::modifyButtonFactory);
        tableColumnBuilder.addButtonToColumn(this::deleteButtonFactory);

        tableCars.getColumns().add(tableColumnBuilder.getColumnWithButtons());
    }

    private Button addButtonFactory() {
        Button addButton = new Button("+ Add");
        addButton.getStyleClass().add("success");
        addButton.setAlignment(Pos.CENTER_RIGHT);

        Car carToAdd = new Car();
        carToAdd.setIdStudent(student.getId());
        addButton.setOnAction(event -> modifyCarAction(carToAdd));
        return addButton;
    }

    private Button modifyButtonFactory() {
        Button modifyButton = getButtonTemplate("Modify");
        modifyButton.getStyleClass().add("accent");
        modifyButton.setOnAction(event -> modifyCarAction(getSelectedItem()));
        return modifyButton;
    }

    private void modifyCarAction(Car car) {
        FormsManager.openCarsChangeFrom(car);
        try {
            reloadTableValues();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Button deleteButtonFactory() {
        Button deleteButton = getButtonTemplate("Delete");
        deleteButton.getStyleClass().add("danger");
        deleteButton.setOnAction(event -> deleteCarAction());
        return deleteButton;
    }

    private Button getButtonTemplate(String buttonName) {
        Button button = new Button(buttonName);
        button.setAlignment(Pos.CENTER);
        button.getStyleClass().addAll("button-outlined", "small");

        return button;
    }

    private void deleteCarAction() {
        Optional<ButtonType> selectedButtonType = FormsManager.openConfirmationDeleteDialog();
        if (selectedButtonType.isPresent() && selectedButtonType.get() == ButtonType.OK) {
            try {
                int carId = getSelectedItem().getId();
                carRepo.delete(carId);
                reloadTableValues();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Car getSelectedItem() {
        return tableCars.getSelectionModel().getSelectedItem();
    }

    private void reloadTableValues() throws IOException {
        tableCars.setItems(FXCollections.observableList(getCars()));
        tableCars.refresh();
    }

    /**
     * Gets all cars that have student ID of the given student
     *
     * @return List of cars or empty if none
     */
    private List<Car> getCars() throws IOException {
        //new repo is created since some info can be changed
        return carRepo
                .takeData()
                .stream()
                .filter(car -> car.getIdStudent() == student.getId())
                .toList();
    }


}
