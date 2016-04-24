package controller;

import com.itextpdf.text.DocumentException;
import entity.Gymrelease;
import entity.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Model;
import model.PDFUtil;
import model.WindowUtil;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by Phips on 26.06.2015.
 */
public class GymreleaseController{

    @FXML
    private DatePicker dpBeginn;
    @FXML
    private DatePicker dpEnd;
    @FXML
    private TextField tfReason;
    @FXML
    private TextArea taAdditionalInfo;
    @FXML
    private Button btnGeneratePDF;
    @FXML
    private Button btnConfirm;
    @FXML
    private Label lbMessage;

    private Student student;
    private Gymrelease gymrelease;

    public void init(Student student, Gymrelease gymrelease) {
        this.student = student;
        this.gymrelease = gymrelease;

        if (gymrelease != null) {
            dpBeginn.setValue(gymrelease.getBeginDateAsDate());
            dpEnd.setValue(gymrelease.getEndDateAsDate());
            tfReason.setText(gymrelease.getReason());
            taAdditionalInfo.setText(gymrelease.getInformation());
        }
    }

    @FXML
    public void onConfirm(ActionEvent event) {
        if (!WindowUtil.checkValid(Arrays.asList(dpBeginn, dpEnd, tfReason), new LinkedList<>()))
            return;

        if (dpEnd.getValue().compareTo(dpBeginn.getValue()) < 0) {
            WindowUtil.showInfoDialog("Beginndatum muss vor dem Enddatum liegen!");
            return;
        }


        //Adds a new Document to the database when it doesn't already exist
        if (gymrelease == null) {
            Model.getModel().addDocument(new Gymrelease(student, taAdditionalInfo.getText(), LocalDate.now(), dpBeginn.getValue(), dpEnd.getValue(), tfReason.getText()));
        }
        else {
            gymrelease.setBeginDate(dpBeginn.getValue());
            gymrelease.setEndDate(dpEnd.getValue());
            gymrelease.setReason(tfReason.getText());
            gymrelease.setInformation(taAdditionalInfo.getText());
            gymrelease.setTimeStamp(LocalDate.now());
            Model.getModel().addDocument(gymrelease);
        }

        tfReason.getScene().getWindow().hide();
    }



    @FXML
    public void onCreatePDF(ActionEvent event) {
        if (WindowUtil.checkValid(Arrays.asList(dpBeginn, dpEnd, tfReason), new LinkedList<>()))
        {

            try {
                //PDFUtil.createGymReleasePDF(student, dpBeginn.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), dpEnd.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), tfReason.getText());
                PDFUtil.createGeneralReleasePDF(student, "Turnen", dpBeginn.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), dpEnd.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), tfReason.getText() + ". " + taAdditionalInfo.getText());
                WindowUtil.setLabelMessageAnimation(lbMessage, "PDF am Desktop gespeichert");
            } catch (DocumentException | FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onExit(MouseEvent event) {
        tfReason.getScene().getWindow().hide();
    }

}
