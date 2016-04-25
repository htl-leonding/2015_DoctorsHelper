package at.controller;

import at.entity.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import at.model.Model;
import at.model.WindowUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * Created by Phips on 28.06.2015.
 */
public class UpdateStudentController {

    private Student oldStudent;

    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfSurName;
    @FXML
    private TextField tfClass;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfPhone;
    @FXML
    private DatePicker dpBirthDate;
    @FXML
    private CheckBox cbMale;
    @FXML
    private CheckBox cbFemale;

    public void init(Student student) {
        this.oldStudent = student;

        //Initializes the Window with the student information
        tfFirstName.setText(oldStudent.getFirstname());
        tfSurName.setText(oldStudent.getLastName());
        tfClass.setText(oldStudent.getClazz());
        dpBirthDate.setValue(LocalDate.parse(oldStudent.birthdateProperty().get(),DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        tfAddress.setText(oldStudent.getAddress());
        tfPhone.setText(oldStudent.getPhone());
        if (oldStudent.getSex().toUpperCase().equals("MALE")) {
            cbMale.setSelected(true);
        } else {
            cbFemale.setSelected(true);
        }
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
    private void onInsert(ActionEvent event) {

        boolean ret = false;

        if (!cbFemale.isSelected() && !cbMale.isSelected()){
            WindowUtil.changeBorderColor(cbFemale, Color.RED);
            WindowUtil.changeBorderColor(cbMale, Color.RED);
            ret = true;
        } else{
            WindowUtil.changeBorderColor(cbFemale, Color.web("#F4F4F4"));
            WindowUtil.changeBorderColor(cbMale, Color.web("#F4F4F4"));
            ret = false;
        }

        if (!WindowUtil.checkValid(Arrays.asList(tfFirstName, tfSurName, tfClass), Arrays.asList(dpBirthDate)) || ret)
            return;

        LocalDate l = dpBirthDate.getValue();

        //Checks if male or female is selected and creates the corresponding students
        Student s;
        if (cbMale.isSelected()) {
            s = new Student(tfFirstName.getText(), tfSurName.getText(), tfClass.getText(),dpBirthDate.getValue(), "MALE", tfAddress.getText(), tfPhone.getText());
        } else if (cbFemale.isSelected()) {
            s = new Student(tfFirstName.getText(), tfSurName.getText(), tfClass.getText(), dpBirthDate.getValue(), "FEMALE", tfAddress.getText(), tfPhone.getText());
        } else return;


            Model.getModel().updateStudent(oldStudent, s);
            tfFirstName.getScene().getWindow().hide();

    }

    @FXML
    private void onExit(MouseEvent event) {
        tfFirstName.getScene().getWindow().hide();
    }
}
