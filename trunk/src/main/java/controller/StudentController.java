/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import entity.Student;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Model;
import model.WindowUtil;

/**
 * FXML controller class
 *
 * @author Lukas
 * revived by Steininger
 */
public class StudentController {
    
    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfSurName;
    @FXML
    private TextField tfPhone;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfClass;
    @FXML
    private DatePicker dpBirthDate;
    @FXML
    private CheckBox cbMale;
    @FXML
    private CheckBox cbFemale;

    @FXML
    private void onInsert(ActionEvent event) {
        if (!WindowUtil.checkValid(Arrays.asList(tfFirstName, tfSurName, tfClass, dpBirthDate), new LinkedList<>()))
            return;

        //Checks if male or female is selected and creates the corresponding students
        Student s;
        if (cbMale.isSelected()) {
            s = new Student(tfFirstName.getText(), tfSurName.getText(), tfClass.getText(), dpBirthDate.getValue(), cbMale.textProperty().get(), tfAddress.getText(), tfPhone.getText());
        } else if (cbFemale.isSelected()) {
            s = new Student(tfFirstName.getText(), tfSurName.getText(), tfClass.getText(), dpBirthDate.getValue(), cbFemale.textProperty().get(), tfAddress.getText(), tfPhone.getText());
        } else return;

        Model.getModel().addStudent(s);
        tfFirstName.getScene().getWindow().hide();
    }

    @FXML
    public void cbMaleClicked(ActionEvent e) {
        if (cbMale.isSelected())
            cbFemale.setSelected(false);
    }

    @FXML
    public void cbFemaleClicked(ActionEvent e) {
        if (cbFemale.isSelected())
            cbMale.setSelected(false);
    }

    @FXML
    private void onExit(MouseEvent event) {
        tfFirstName.getScene().getWindow().hide();
    }
}
