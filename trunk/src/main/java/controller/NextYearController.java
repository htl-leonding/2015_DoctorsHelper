package controller;

import entity.Disease;
import entity.Student;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.Model;
import model.WindowUtil;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;

/**
 * Created by Phips on 26.06.2015.
 */
public class NextYearController {

    @FXML
    private ComboBox cbLastYear;

    public void init() {
        List<String> options = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8");
        cbLastYear.setItems(FXCollections.observableList(options));
    }


    public void onRun(ActionEvent actionEvent) {
        if (cbLastYear.getSelectionModel().getSelectedItem() == null)
            WindowUtil.showInfoDialog("Letzten Jahrgang auswählen");
        else {

            int lastYear = Integer.parseInt((String)cbLastYear.getSelectionModel().getSelectedItem());
            List<Student> allStudents = Model.getModel().getStudentList();
            for (Student s: allStudents) {
                String actClazz = "";
                for (int i = 0; i < s.getClazz().toCharArray().length; i++){
                    char act = s.getClazz().charAt(i);
                    if (Character.isDigit(act)){
                        int number = Integer.parseInt(Character.toString(act));
                        if (number == lastYear){
                            actClazz = "Schulabgänger";
                            break;
                        }
                        act = String.valueOf(number+1).charAt(0);
                    }
                    actClazz += act;
                }
                s.setClazz(actClazz);
            }

            for (Student s: allStudents ) {
                Model.getModel().mergeStudent(s);
            }

            cbLastYear.getScene().getWindow().hide();
        }
    }

}
