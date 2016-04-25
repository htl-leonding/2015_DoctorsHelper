package at.controller;

import com.itextpdf.text.DocumentException;
import at.entity.Disease;
import at.entity.Student;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import at.model.Model;
import at.model.PDFUtil;
import at.model.WindowUtil;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by Phips on 26.06.2015.
 */
public class DiseaseController {

    @FXML
    private TextField tfDisease;
    @FXML
    private ComboBox cbLevel;
    @FXML
    private DatePicker dpDate;
    @FXML
    private TextArea taAdditionalInformation;
    @FXML
    private Label lbPdfSaved;

    private Student student;
    private Disease disease;

    public void init(Student student, Disease disease) {
        this.student = student;
        this.disease = disease;
        cbLevel.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));

        if (disease != null) {
            tfDisease.setText(disease.getInformation());
            cbLevel.setValue(disease.getLevel());
            dpDate.setValue(disease.getBeginDateAsDate());
            taAdditionalInformation.setText(disease.getAdditionalInformation());
        }
    }

    @FXML
     public void onSave(ActionEvent event) {
        if (!WindowUtil.checkValid(Arrays.asList(tfDisease, cbLevel, dpDate), new LinkedList<>()))
            return;

        //Adds a new Document to the database when it doesn't already exist
        if (disease == null) {
            Model.getModel().addDocument(new Disease(student, tfDisease.getText(), LocalDate.now(), Integer.parseInt(cbLevel.getSelectionModel().getSelectedItem().toString()), dpDate.getValue(), taAdditionalInformation.getText()));
        }
        else {
            disease.setInformation(tfDisease.getText());
            disease.setLevel((Integer) cbLevel.getValue());
            disease.setBeginDateAsDate(dpDate.getValue());
            disease.setAdditionalInformation(taAdditionalInformation.getText());
            disease.setTimeStamp(LocalDate.now());
            Model.getModel().addDocument(disease);
        }

        tfDisease.getScene().getWindow().hide();
    }

    @FXML
    public void onExit(MouseEvent event) {
        tfDisease.getScene().getWindow().hide();
    }

    public void onSavePdf(ActionEvent actionEvent) {
        if (WindowUtil.checkValid(Arrays.asList(dpDate, cbLevel, tfDisease), new LinkedList<>()))
        {
            try {
                PDFUtil.createGeneralReleasePDF(student, "Generell", dpDate.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), dpDate.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), tfDisease.getText() + ". " + taAdditionalInformation.getText());
                WindowUtil.setLabelMessageAnimation(lbPdfSaved, "PDF am Desktop gespeichert");
            } catch (DocumentException | FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
