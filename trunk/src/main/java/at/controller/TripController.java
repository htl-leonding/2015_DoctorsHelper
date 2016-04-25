package at.controller;

import at.entity.Student;
import at.entity.Trip;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import at.model.Model;
import at.model.PDFUtil;
import at.model.WindowUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Phips on 26.06.2015.
 */
public class TripController {

    @FXML
    private TextField tfName;
    @FXML
    private ComboBox cbClazz;
    @FXML
    private ComboBox cbTrips;
    @FXML
    private ListView lvUnselected;
    @FXML
    private ListView lvSelected;
    @FXML
    private ListView lvAbsent;
    @FXML
    private Button btSave;
    @FXML
    private Label lbPdfSaved;

    private Model model;

    private List<Student> selectedStudents;
    private List<Student> unselectedStudents;
    private List<Student> absentStudents;

    private Trip actTrip;

    public void init() {
        cbClazz.setItems(Model.getModel().getAllClasses());
        cbTrips.setItems(FXCollections.observableArrayList(Model.getModel().getAllTrips()));
        model = Model.getModel();
        selectedStudents = new LinkedList<>();
        unselectedStudents = new LinkedList<>();
        absentStudents = new LinkedList<>();

        cbClazz.valueProperty().addListener((observable, oldValue, newValue) -> {

           if (newValue == null) return;

            selectedStudents.clear();
            absentStudents.clear();

            String clazz = (String) newValue;

            if (newValue.equals("Alle")){
                unselectedStudents =FXCollections.observableList(model.getAllStudents());
            } else{
                unselectedStudents = model.getStudentByClass(clazz);
            }

            updateListViews();
        });

        cbTrips.valueProperty().addListener((observable, oldValue, newValue) -> {
            actTrip = (Trip)newValue;
            btSave.setText("Update");

            tfName.setText(actTrip.getName());
            cbClazz.getSelectionModel().select(actTrip.getClazz());
            absentStudents = actTrip.getAbsentStudents();
            unselectedStudents = actTrip.getUnselectedStudents();
            selectedStudents = actTrip.getSelectedStudents();
            updateListViews();
        });
    }

    @FXML
    void onCancel(ActionEvent event) {
        tfName.getScene().getWindow().hide();
    }

    @FXML
    void onSave(ActionEvent event) {
        if (tfName.getText().isEmpty() || absentStudents.size() + unselectedStudents.size() + selectedStudents.size() == 0){
            WindowUtil.showInfoDialog("Es müssen alle Informationen eingegeben werden. Bitte kontrollieren Sie, ob der Veranstaltungsname gesetzt und Schüler ausgewählt wurden.");
        }
        else {

            Trip trip = new Trip(absentStudents, unselectedStudents, selectedStudents, (String) cbClazz.getSelectionModel().getSelectedItem(), tfName.getText());

            if (actTrip == null) {
                model.addTrip(trip);
            } else {
                actTrip.setName(tfName.getText());
                actTrip.setAbsentStudents(absentStudents);
                actTrip.setUnselectedStudents(unselectedStudents);
                actTrip.setSelectedStudents(selectedStudents);
                actTrip.setClazz((String) cbClazz.getSelectionModel().getSelectedItem());
                model.updateTrip(actTrip);
            }
            tfName.getScene().getWindow().hide();
        }
    }

    private void updateListViews(){
        lvSelected.setItems(FXCollections.observableList(selectedStudents));
        lvUnselected.setItems(FXCollections.observableList(unselectedStudents));
        lvAbsent.setItems(FXCollections.observableList(absentStudents));
    }

    //<editor-fold desc="Shuttle Methods">

    public void onSingleSelected(MouseEvent event) {
        singleAction(lvUnselected, true);
    }

    public void onSingleUnselected(MouseEvent event) {
        singleAction(lvSelected, false);
    }

    public void onAllSelected(MouseEvent event) {
        selectedStudents.addAll((List<Student>) lvUnselected.getItems());
        unselectedStudents.clear();
        updateListViews();
    }

    public void onAllUnselected(MouseEvent event) {
        unselectedStudents.addAll((List<Student>) lvSelected.getItems());
        selectedStudents.clear();
        updateListViews();
    }


    public void onSingleAbsent(MouseEvent event) {
        singleAction2(lvUnselected, true);
    }

    public void onSingleUnAbsent(MouseEvent event) {
        singleAction2(lvAbsent, false);
    }

    public void onAllAbsent(MouseEvent event) {
        absentStudents.addAll((List<Student>) lvUnselected.getItems());
        unselectedStudents.clear();
        updateListViews();
    }

    public void onAllUnAbsent(MouseEvent event) {
        unselectedStudents.addAll((List<Student>) lvAbsent.getItems());
        absentStudents.clear();
        updateListViews();
    }

    private void singleAction(ListView lv, boolean selected){
        if (lv.getSelectionModel().getSelectedItem() != null){
            Student s = (Student) lv.getSelectionModel().getSelectedItem();

            if (selected){
                selectedStudents.add(s);
                unselectedStudents.remove(s);
            } else{
                unselectedStudents.add(s);
                selectedStudents.remove(s);
            }

            updateListViews();
        }
    }

    private void singleAction2(ListView lv, boolean absent){
        if (lv.getSelectionModel().getSelectedItem() != null){
            Student s = (Student) lv.getSelectionModel().getSelectedItem();

            if (absent){
                absentStudents.add(s);
                unselectedStudents.remove(s);
            } else{
                unselectedStudents.add(s);
                absentStudents.remove(s);
            }

            updateListViews();
        }
    }

    //</editor-fold>

    public void onPdf(ActionEvent actionEvent) {
        if (tfName.getText().isEmpty() || absentStudents.size() + unselectedStudents.size() + selectedStudents.size() == 0){
            WindowUtil.showInfoDialog("Es müssen alle Informationen eingegeben werden. Bitte kontrollieren Sie, ob der Veranstaltungsname gesetzt und Schüler ausgewählt wurden.");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            try {
                PDFUtil.createSchoolTripPDF(tfName.getText(), LocalDate.now().format(formatter),
                        (String) cbClazz.getSelectionModel().getSelectedItem(), "", selectedStudents.size(),
                        unselectedStudents, absentStudents);
                WindowUtil.setLabelMessageAnimation(lbPdfSaved, "PDF am Desktop gespeichert");
            } catch(Exception e){

            }
        }

    }

    public void onShowAllTrips(ActionEvent actionEvent) {

    }

    public List<Student> cloneList(List<Student> list){
        List<Student> retList=new LinkedList<>();


        return null;
    }
}
