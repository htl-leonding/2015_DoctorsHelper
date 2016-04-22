package controller;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;
import entity.Checkup;
import entity.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import model.Model;

import java.time.LocalDate;

/**
 * Created by Lukas on 07.03.2016.
 */
public class CheckupController {


    //region fxElements
    @FXML // fx:id="tfSpecialistExamination"
    private TextArea tfSpecialistExamination; // Value injected by FXMLLoader

    @FXML // fx:id="sprachfehler"
    private ToggleGroup sprachfehler; // Value injected by FXMLLoader

    @FXML // fx:id="tfHeight"
    private TextField tfHeight; // Value injected by FXMLLoader

    @FXML // fx:id="tfWeight"
    private TextField tfWeight; // Value injected by FXMLLoader

    @FXML // fx:id="cbSaniert"
    private CheckBox cbSaniert; // Value injected by FXMLLoader

    @FXML // fx:id="lunge"
    private ToggleGroup lunge; // Value injected by FXMLLoader

    @FXML // fx:id="wirbelsäule"
    private ToggleGroup wirbelsäule; // Value injected by FXMLLoader

    @FXML // fx:id="visus"
    private ToggleGroup visus; // Value injected by FXMLLoader

    @FXML // fx:id="medicalCheckup"
    private ToggleGroup medicalCheckup; // Value injected by FXMLLoader

    @FXML // fx:id="lbSex"
    private Label lbSex; // Value injected by FXMLLoader

    @FXML // fx:id="cbGesund"
    private CheckBox cbGesund; // Value injected by FXMLLoader

    @FXML // fx:id="cbUntersMSpatel"
    private CheckBox cbUntersMSpatel; // Value injected by FXMLLoader

    @FXML // fx:id="tfInTreatment"
    private TextArea tfInTreatment; // Value injected by FXMLLoader

    @FXML // fx:id="tonsillen"
    private ToggleGroup tonsillen; // Value injected by FXMLLoader

    @FXML // fx:id="impfung"
    private ToggleGroup impfung; // Value injected by FXMLLoader

    @FXML // fx:id="schilddrüse"
    private ToggleGroup schilddrüse; // Value injected by FXMLLoader

    @FXML // fx:id="tfExcursion"
    private TextArea tfExcursion; // Value injected by FXMLLoader

    @FXML // fx:id="lbLastname"
    private Label lbLastname; // Value injected by FXMLLoader

    @FXML // fx:id="brillenträger"
    private ToggleGroup brillenträger; // Value injected by FXMLLoader

    @FXML // fx:id="lbFirstname"
    private Label lbFirstname; // Value injected by FXMLLoader

    @FXML // fx:id="arme"
    private ToggleGroup arme; // Value injected by FXMLLoader

    @FXML // fx:id="hörvermögen"
    private ToggleGroup hörvermögen; // Value injected by FXMLLoader

    @FXML // fx:id="haut"
    private ToggleGroup haut; // Value injected by FXMLLoader

    @FXML // fx:id="Excursion"
    private ToggleGroup Excursion; // Value injected by FXMLLoader

    @FXML // fx:id="bauch"
    private ToggleGroup bauch; // Value injected by FXMLLoader

    @FXML // fx:id="beine"
    private ToggleGroup beine; // Value injected by FXMLLoader

    @FXML // fx:id="asthma"
    private ToggleGroup asthma; // Value injected by FXMLLoader

    @FXML // fx:id="cbUntersMSpiegelUSonde"
    private CheckBox cbUntersMSpiegelUSonde; // Value injected by FXMLLoader

    @FXML // fx:id="tfOtherDiagnoses"
    private TextArea tfOtherDiagnoses; // Value injected by FXMLLoader

    @FXML // fx:id="lbBirthdate"
    private Label lbBirthdate; // Value injected by FXMLLoader

    @FXML // fx:id="borderPane"
    private BorderPane borderPane; // Value injected by FXMLLoader

    @FXML // fx:id="zuBehandlung"
    private ToggleGroup zuBehandlung; // Value injected by FXMLLoader

    @FXML // fx:id="nasen"
    private ToggleGroup nasen; // Value injected by FXMLLoader

    @FXML // fx:id="gebis"
    private ToggleGroup gebis; // Value injected by FXMLLoader

    @FXML // fx:id="herz"
    private ToggleGroup herz; // Value injected by FXMLLoader

    @FXML // fx:id="allergie"
    private ToggleGroup allergie; // Value injected by FXMLLoader

    @FXML // fx:id="cbKariös"
    private CheckBox cbKariös; // Value injected by FXMLLoader

    @FXML // fx:id="medicaloverwatch"
    private ToggleGroup medicaloverwatch; // Value injected by FXMLLoader

    @FXML // fx:id="nervensystem"
    private ToggleGroup nervensystem; // Value injected by FXMLLoader

    @FXML // fx:id="diabetes"
    private ToggleGroup diabetes; // Value injected by FXMLLoader

    @FXML // fx:id="schielen"
    private ToggleGroup schielen; // Value injected by FXMLLoader
//endregion

    Student actualStudent;
    Checkup checkup;

    public void init(Student student, Checkup c){
        ObservableList<String> list= FXCollections.observableArrayList();

        double d = Screen.getPrimary().getVisualBounds().getHeight();

        borderPane.setMaxHeight(Screen.getPrimary().getVisualBounds().getHeight()-10);

        lbFirstname.setText(student.getFirstname());
        lbLastname.setText(student.getLastName());
        lbBirthdate.setText(student.getBirthdate().toString());
        lbSex.setText(student.getSex());
        RegularExpression regex= new RegularExpression("/^[0-9]+(\\.[0-9]+)?$");


        tfHeight.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                try{

                    Double.parseDouble(tfHeight.getText());
                }
                catch(Exception e){
                    StringBuilder sb = new StringBuilder(tfHeight.getText());
                    tfHeight.setText(sb.substring(0,tfHeight.getLength()-1));
                    tfHeight.positionCaret(tfHeight.getLength());
                }
            }
        });

        tfWeight.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                try{

                    Double.parseDouble(tfWeight.getText());
                }

                catch(Exception e){
                    StringBuilder sb = new StringBuilder(tfWeight.getText());
                    tfWeight.setText(sb.substring(0,tfWeight.getLength()-1));
                    tfWeight.positionCaret(tfWeight.getLength());
                }
            }
        });




        list.add("Ja");
        list.add("Nein");

        if(c==null)
        checkup = new Checkup(student, LocalDate.now());
        else {
            checkup = c;

            for (Toggle toggle : asthma.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getAsthma().getDescription().toLowerCase()))
                    rb.setSelected(true);
            }
            for (Toggle toggle : impfung.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                String a = rb.getText().toLowerCase();
                String b =checkup.getBcg().getDescription().toLowerCase();
                if(rb.getText().toLowerCase().equals(checkup.getBcg().getDescription().toLowerCase()))
                    rb.setSelected(true);
            }
            for (Toggle toggle : allergie.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getAllergie().getDescription().toLowerCase()))
                    rb.setSelected(true);
            }
            for (Toggle toggle : diabetes.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getDiabetes().getDescription().toLowerCase()))
                    rb.setSelected(true);
            }
            for (Toggle toggle : brillenträger.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getBrillenträger().getDescription().toLowerCase()))
                    rb.setSelected(true);
            }
            for (Toggle toggle : schielen.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getSchielen().getDescription().toLowerCase()))
                    rb.setSelected(true);
            }
            for (Toggle toggle : visus.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getVisus().getDescription().toLowerCase()))
                    rb.setSelected(true);

            }
            for (Toggle toggle : hörvermögen.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getHörvermögen().getDescription().toLowerCase()))
                    rb.setSelected(true);

            }
            for (Toggle toggle : sprachfehler.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getSprachfehler().getDescription().toLowerCase()))
                    rb.setSelected(true);

            }
            for (Toggle toggle : nasen.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getNase().getDescription().toLowerCase()))
                    rb.setSelected(true);

            }
            for (Toggle toggle :tonsillen.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getTonsillen().getDescription().toLowerCase()))
                    rb.setSelected(true);

            }
            for (Toggle toggle : gebis.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getGebisstellung().getDescription().toLowerCase()))
                    rb.setSelected(true);

            }
            for (Toggle toggle : schilddrüse.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getSchilddrüse().getDescription().toLowerCase()))
                    rb.setSelected(true);

            }
            for (Toggle toggle : haut.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getHaut().getDescription().toLowerCase()))
                    rb.setSelected(true);

            }
            for (Toggle toggle : herz.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getHerz().getDescription().toLowerCase()))
                    rb.setSelected(true);

            }
            for (Toggle toggle : lunge.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getLunge().getDescription().toLowerCase()))
                    rb.setSelected(true);

            }
            for (Toggle toggle : bauch.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getBauch().getDescription().toLowerCase()))
                    rb.setSelected(true);

            }
            for (Toggle toggle : wirbelsäule.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getWirbelsäule().getDescription().toLowerCase()))
                    rb.setSelected(true);

            }
            for (Toggle toggle : arme.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getArme().getDescription().toLowerCase()))
                    rb.setSelected(true);

            }
            for (Toggle toggle : beine.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getBeine().getDescription().toLowerCase()))
                    rb.setSelected(true);

            }
            for (Toggle toggle : nervensystem.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getNerven().getDescription().toLowerCase()))
                    rb.setSelected(true);

            }
            for (Toggle toggle : medicalCheckup.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getSpecialistExamination().getDescription().toLowerCase()))
                    rb.setSelected(true);

            }
            for (Toggle toggle : zuBehandlung.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getTreatment().getDescription().toLowerCase()))
                    rb.setSelected(true);

            }
            for (Toggle toggle : Excursion.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getReadyForExcursion().getDescription().toLowerCase()))
                    rb.setSelected(true);

            }
            for (Toggle toggle : medicaloverwatch.getToggles() )
            {
                RadioButton rb = (RadioButton) toggle;
                if(rb.getText().toLowerCase().equals(checkup.getSchoolHealthMonitoring().getDescription().toLowerCase()))
                    rb.setSelected(true);

            }

            for (Checkup.ZähneTyp zahn:checkup.getZähne()) {

                if(zahn== Checkup.ZähneTyp.gesund){
                    cbGesund.setSelected(true);
                }
                else if(zahn == Checkup.ZähneTyp.kariös)
                {
                    cbKariös.setSelected(true);
                }
                else if(zahn == Checkup.ZähneTyp.UntersMSpiegelUSonde)
                {
                    cbUntersMSpiegelUSonde.setSelected(true);
                }
                else if(zahn == Checkup.ZähneTyp.saniert)
                {
                    cbSaniert.setSelected(true);
                }
                else if(zahn == Checkup.ZähneTyp.UntersMSpatel)
                {
                    cbUntersMSpatel.setSelected(true);
                }

            }


            tfExcursion.setText(checkup.getReadyForExcursionName());
            tfInTreatment.setText(checkup.getTreatmentBecauseOf());
            tfOtherDiagnoses.setText(checkup.getOtherDiagnoses());
            tfSpecialistExamination.setText(checkup.getSpecialistExaminationBecauseOf());
            tfHeight.setText(checkup.getHeight().toString());
            tfWeight.setText(checkup.getWeight().toString());

        }




        System.out.println(diabetes.selectedToggleProperty().getName());
    }

    @FXML
    void onSave(ActionEvent actionEvent){
    String text=((RadioButton)asthma.getSelectedToggle()).getText();
        switch(((RadioButton)asthma.getSelectedToggle()).getText()){
            case "JA":
                checkup.setAsthma(Checkup.AsthmaTyp.Ja);
                break;

            case "Nein":
                checkup.setAsthma(Checkup.AsthmaTyp.Nein);
                break;
        }

        switch(((RadioButton)impfung.getSelectedToggle()).getText()){
            case "JA":
                checkup.setBcg(Checkup.BCGTyp.Ja);
                break;

            case "Nein":
                checkup.setBcg(Checkup.BCGTyp.Nein);
                break;
        }

        switch(((RadioButton)allergie.getSelectedToggle()).getText()){
            case "JA":
                checkup.setAllergie(Checkup.AllergieTyp.Ja);
                break;

            case "Nein":
                checkup.setAllergie(Checkup.AllergieTyp.Nein);
                break;
        }

        switch(((RadioButton)diabetes.getSelectedToggle()).getText()){
            case "JA":
                checkup.setDiabetes(Checkup.DiabetesTyp.Ja);
                break;

            case "Nein":
                checkup.setDiabetes(Checkup.DiabetesTyp.Nein);
                break;
        }

        switch(((RadioButton)brillenträger.getSelectedToggle()).getText()){
            case "JA":
                checkup.setBrillenträger(Checkup.BrillenträgerTyp.Ja);
                break;

            case "Nein":
                checkup.setBrillenträger(Checkup.BrillenträgerTyp.Nein);
                break;
        }

        switch(((RadioButton)schielen.getSelectedToggle()).getText()){
            case "JA":
                checkup.setSchielen(Checkup.SchielenTyp.Ja);
                break;

            case "Nein":
                checkup.setSchielen(Checkup.SchielenTyp.Nein);
                break;
        }

        switch(((RadioButton)visus.getSelectedToggle()).getText()){
            case "6/6 bds.":
                checkup.setVisus(Checkup.VisusTyp.bds);
                break;

            case "einseitig herabgesetzt":
                checkup.setVisus(Checkup.VisusTyp.einseitigHerabgesetzt);
                break;
            case "beidseitig herabgesetzt":
                checkup.setVisus(Checkup.VisusTyp.beidseitigHerabgesetzt);
                break;
        }

        switch(((RadioButton)hörvermögen.getSelectedToggle()).getText()){
            case "normal":
                checkup.setHörvermögen(Checkup.HörvermögenTyp.normal);
                break;

            case "einseitig behindert":
                checkup.setHörvermögen(Checkup.HörvermögenTyp.einseitigBehindert);
                break;
            case "beidseitig behindert":
                checkup.setHörvermögen(Checkup.HörvermögenTyp.beidseitigBehindert);
                break;
        }

        switch(((RadioButton)sprachfehler.getSelectedToggle()).getText()){
            case "JA":
                checkup.setSprachfehler(Checkup.SprachfehlerTyp.Ja);
                break;

            case "Nein":
                checkup.setSprachfehler(Checkup.SprachfehlerTyp.Nein);
                break;
        }

        switch(((RadioButton)nasen.getSelectedToggle()).getText()){
            case "frei":
                checkup.setNase(Checkup.NaseTyp.frei);
                break;

            case "Atmung chronisch behindert":
                checkup.setNase(Checkup.NaseTyp.AtmungchronBehindert);
                break;
        }

        switch(((RadioButton)tonsillen.getSelectedToggle()).getText()){
            case "normal":
                checkup.setTonsillen(Checkup.TonsillenTyp.normal);
                break;

            case "krankhaft verändert":
                checkup.setTonsillen(Checkup.TonsillenTyp.krankhaftVerändert);
                break;
            case "entfernt":
                checkup.setTonsillen(Checkup.TonsillenTyp.entfernt);
                break;
        }


        if(cbGesund.isSelected()){
            checkup.addZähne(Checkup.ZähneTyp.gesund);
        }
        if(cbKariös.isSelected()){
            checkup.addZähne(Checkup.ZähneTyp.kariös);
        }
        if(cbSaniert.isSelected()){
            checkup.addZähne(Checkup.ZähneTyp.saniert);
        }
        if(cbUntersMSpatel.isSelected()){
            checkup.addZähne(Checkup.ZähneTyp.UntersMSpatel);
        }
        if(cbUntersMSpiegelUSonde.isSelected()){
            checkup.addZähne(Checkup.ZähneTyp.UntersMSpiegelUSonde);
        }


        switch(((RadioButton)gebis.getSelectedToggle()).getText()){
            case "normal":
                checkup.setGebisstellung(Checkup.GebisstellungTyp.normal);
                break;

            case "Fehlstellung in Behandlung":
                checkup.setGebisstellung(Checkup.GebisstellungTyp.fehlstellungInBehandl);
                break;

            case "Fehlstellung o. Behandl.":
                checkup.setGebisstellung(Checkup.GebisstellungTyp.fehlstellungOBehandlung);
                break;
        }

        switch(((RadioButton)schilddrüse.getSelectedToggle()).getText()){
            case "normal":
                checkup.setSchilddrüse(Checkup.SchilddrüseTyp.normal);
                break;

            case "nur tastbar vergrößert":
                checkup.setSchilddrüse(Checkup.SchilddrüseTyp.nurTastbarVergrößert);
                break;

            case "sichtbar vergrößert":
                checkup.setSchilddrüse(Checkup.SchilddrüseTyp.sichtbarVergrößert);
                break;
        }

        switch(((RadioButton)haut.getSelectedToggle()).getText()){
            case "normal":
                checkup.setHaut(Checkup.HautTyp.normal);
                break;

            case "chron. Hautleiden":
                checkup.setHaut(Checkup.HautTyp.chronHAutleiden);
                break;
        }

        switch(((RadioButton)herz.getSelectedToggle()).getText()){
            case "normal":
                checkup.setHerz(Checkup.HerzTyp.normal);
                break;

            case "organ. Herz- und Gefäßerkrankung":
                checkup.setHerz(Checkup.HerzTyp.organHerzUndGefäß);
                break;
        }

        switch(((RadioButton)lunge.getSelectedToggle()).getText()){
            case "normal":
                checkup.setLunge(Checkup.LungTyp.normal);
                break;

            case "chron. rezid. Bronchitis":
                checkup.setLunge(Checkup.LungTyp.chronBronchitis);
                break;

            case "Asthma":
                checkup.setLunge(Checkup.LungTyp.Asthma);
                break;
        }

        switch(((RadioButton)bauch.getSelectedToggle()).getText()){
            case "normal":
                checkup.setBauch(Checkup.BauchTyp.normal);
                break;

            case "Hernien":
                checkup.setBauch(Checkup.BauchTyp.hernien);
                break;

            case "Sonstiges":
                checkup.setBauch(Checkup.BauchTyp.sonstiges);
                break;

        }

        switch(((RadioButton)wirbelsäule.getSelectedToggle()).getText()){
            case "normal":
                checkup.setWirbelsäule(Checkup.WirbelsäuleTyp.normal);
                break;

            case "Haltungsschwäche":
                checkup.setWirbelsäule(Checkup.WirbelsäuleTyp.haltungsschwäche);
                break;

            case "Fehlform":
                checkup.setWirbelsäule(Checkup.WirbelsäuleTyp.fehlform);
                break;


        }

        switch(((RadioButton)arme.getSelectedToggle()).getText()){
            case "normal":
                checkup.setArme(Checkup.ArmeTyp.normal);
                break;

            case "Fehlform o. Funktionsbeh.":
                checkup.setArme(Checkup.ArmeTyp.fehlformOFunktionsbehinderung);
                break;

            case "Fehlform m. Funktionsbeh.":
                checkup.setArme(Checkup.ArmeTyp.fehlformMFunktionsbehinderung);
                break;
        }

        switch(((RadioButton)beine.getSelectedToggle()).getText()){
            case "normal":
                checkup.setBeine(Checkup.BeineTyp.normal);
                break;

            case "Fehlform o. Funktionsbeh.":
                checkup.setBeine(Checkup.BeineTyp.fehlformOFunktionsbehinderung);
                break;

            case "Fehlform m. Funktionsbeh.":
                checkup.setBeine(Checkup.BeineTyp.fehlformMFunktionsbehinderung);
                break;
        }

        switch(((RadioButton)nervensystem.getSelectedToggle()).getText()){
            case "normal":
                checkup.setNerven(Checkup.NervenTyp.normal);
                break;

            case "veget. und psych. Labil":
                checkup.setNerven(Checkup.NervenTyp.vegetUPsychLabil);
                break;

            case "organ. Nervenleiden":
                checkup.setNerven(Checkup.NervenTyp.organNervenleiden);
                break;
        }

        switch(((RadioButton)medicalCheckup.getSelectedToggle()).getText()){
            case "JA":
                checkup.setSpecialistExamination(Checkup.specialistExaminationType.Ja);
                break;

            case "Nein":
                checkup.setSpecialistExamination(Checkup.specialistExaminationType.Nein);
                break;

        }

        switch(((RadioButton)zuBehandlung.getSelectedToggle()).getText()){
            case "JA":
                checkup.setTreatment(Checkup.treatmentType.Ja);
                break;

            case "Nein":
                checkup.setTreatment(Checkup.treatmentType.Nein);
                break;

        }

        switch(((RadioButton)medicaloverwatch.getSelectedToggle()).getText()){
            case "JA":
                checkup.setSchoolHealthMonitoring(Checkup.schoolHealthMonitoringType.Ja);
                break;

            case "Nein":
                checkup.setSchoolHealthMonitoring(Checkup.schoolHealthMonitoringType.Nein);
                break;

        }

        switch(((RadioButton)Excursion.getSelectedToggle()).getText()){
            case "bedingt geeignet":
                checkup.setReadyForExcursion(Checkup.ReadyForExcursionType.bedingtGeeignet);
                break;

            case "nicht geeignet":
                checkup.setReadyForExcursion(Checkup.ReadyForExcursionType.nichtGeeignet);
                break;

        }

        checkup.setOtherDiagnoses(tfOtherDiagnoses.getText());
        checkup.setSpecialistExaminationBecauseOf(tfSpecialistExamination.getText());
        checkup.setTreatmentBecauseOf(tfInTreatment.getText());
        checkup.setReadyForExcursionName(tfExcursion.getText());
        if(tfWeight.getLength()!=0)
        checkup.setWeight(Double.parseDouble(tfWeight.getText()));
        else checkup.setWeight(0.0);
        if(tfHeight.getLength()!=0)
        checkup.setHeight(Double.parseDouble(tfHeight.getText()));
        else checkup.setHeight(0.0);

        persistToDB();

        lbBirthdate.getScene().getWindow().hide();
    }


    public void persistToDB(){
        Model.getModel().addDocument(checkup);
    }

    @FXML
    void onCancle(ActionEvent event) {
        lbBirthdate.getScene().getWindow().hide();
    }

}
