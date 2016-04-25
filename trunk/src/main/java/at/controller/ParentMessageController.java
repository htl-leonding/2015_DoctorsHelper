package at.controller;

import com.itextpdf.text.DocumentException;
import at.entity.ParentMessage;
import at.entity.Student;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import at.model.Model;
import at.model.PDFUtil;
import at.model.WindowUtil;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Phips on 17.08.2015.
 */
public class ParentMessageController {

    @FXML
    public TextField tfProblem;
    @FXML
    public TextArea taText;
    @FXML
    public Label lbMessage;
    @FXML
    public ComboBox comboBox;
    @FXML
    public TextField tfDocType;
    @FXML
    public ListView lvProblems;


    Student student;
    ParentMessage pM;
    List<String> problems;

    public void init(Student student, ParentMessage pM) {
        this.student = student;
        this.pM = pM;

        problems = new ArrayList<>();
        //Initializes the window with the data of pM when it exists

        if (pM != null) {
            tfProblem.setText(pM.getProblem());
            tfDocType.setText(pM.getDoctor());
            comboBox.getSelectionModel().select(pM.getDoctorType());
        }
        else
        {
            this.pM=new ParentMessage();
            this.pM.setStudent(student);

        }

    }

    @FXML
    public void onSave(ActionEvent event) {

        if (problems.size() > 0){
            if (!WindowUtil.checkNotNull(Arrays.asList(tfDocType, comboBox))){
                WindowUtil.changeBorderColor(tfProblem, Color.web("F4F4F4"));
                return;
            }

        } else{
            WindowUtil.changeBorderColor(tfProblem, Color.RED);
            if (!WindowUtil.checkNotNull(Arrays.asList(tfDocType, comboBox)))
                return;
        }



        //Adds a new Document to the database when it doesn't already exist
        //if (pM == null) {
            //Model.getModel().addDocument(new ParentMessage(student, taText.getText(), LocalDate.now(), tfHeader.getText()));
        //} else {
            //pM.setHeader(tfHeader.getText());
        pM.setDoctor(tfDocType.getText());
        pM.setDoctorType((String) comboBox.getSelectionModel().getSelectedItem());
        pM.setProblem(tfProblem.getText());
            pM.setInformation(tfDocType.getText());
            pM.setTimeStamp(LocalDate.now());
            Model.getModel().addDocument(pM);
        //}
        tfDocType.getScene().getWindow().hide();
    }

    @FXML
    public void onCreatePDF(ActionEvent event) {

        if(comboBox.getSelectionModel().getSelectedItem() == null)
            return;

        String docType = (String) comboBox.getSelectionModel().getSelectedItem();
        String specialDoc = "noSpecDoc";
        //Student student, String date, String doctorType, String additionalInfos, String signatureName, java.util.List<String> problems){


        if(docType.equals("Facharzt")) {
            if (tfDocType.getText().equals(""))
                return;

            specialDoc = tfDocType.getText();
        }

        try {
                //PDFUtil.createPDFLetterFromString(taText.getText(), tfHeader.getText());
                PDFUtil.createParentsPDF(student, LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), docType, specialDoc, "signaturName", problems);
                WindowUtil.setLabelMessageAnimation(lbMessage, "PDF am Desktop gespeichert");
            } catch (DocumentException | FileNotFoundException e) {
                e.printStackTrace();
            }

    }

    @FXML
    public void onExit(MouseEvent event) {
        comboBox.getScene().getWindow().hide();
    }

    @FXML
    public void onAddProblem(ActionEvent actionEvent) {
        if (tfProblem != null)
            if(tfProblem.getText() != ""){
                problems.add(tfProblem.getText());
                tfProblem.setText("");
                lvProblems.setItems(FXCollections.observableList(problems));
            }



    }
}
