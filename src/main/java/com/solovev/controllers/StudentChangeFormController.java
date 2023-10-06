package com.solovev.controllers;

import com.solovev.model.Student;
import com.solovev.repositories.Repository;
import com.solovev.repositories.StudentRepository;
import com.solovev.util.FormsManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class StudentChangeFormController implements ControllerData<Student> {
    @FXML
    public TextField nameField;
    @FXML
    public TextField numField;
    @FXML
    public TextField salaryField;
    @FXML
    public TextField ageField;
    private Student student;

    @FXML
    public void saveButton(ActionEvent actionEvent) throws IOException {
        try {
            Repository<Student> repo = new StudentRepository();
            boolean updateSuccess = updateStudentWithFields();

            if (updateSuccess) {
                int studentsId = student.getId();
                // choose between save or add methods
                if (repo.contains(studentsId)) {
                    repo.replace(student);
                } else {
                    repo.add(student);
                }
                FormsManager.closeWindow(actionEvent);
            }
        } catch (IllegalArgumentException e) {
            FormsManager.showAlertWithoutHeaderText("Illegal argument", e.toString(), Alert.AlertType.WARNING);
        }
    }

    private boolean updateStudentWithFields() {
        boolean updateSuccess = false;
        try {
            int age = Integer.parseInt(ageField.getText());
            int num = Integer.parseInt(numField.getText());
            double salary = Double.parseDouble(salaryField.getText());

            student.setName(nameField.getText());
            student.setAge(age);
            student.setNum(num);
            student.setSalary(salary);
            updateSuccess = true;
        } catch (NumberFormatException e) {
            FormsManager.showAlertWithoutHeaderText("Number format exception", e.toString(), Alert.AlertType.WARNING);
        }
        return updateSuccess;
    }

    @FXML
    public void declineButton(ActionEvent actionEvent) {
        FormsManager.closeWindow(actionEvent);
    }

    @Override
    public void initData(Student student) {
        nameField.setText(student.getName());
        ageField.setText(String.valueOf(student.getAge()));
        numField.setText(String.valueOf(student.getNum()));
        salaryField.setText(String.valueOf(student.getSalary()));

        this.student = student;
    }

}
