package at.controller;

import at.entity.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.stage.*;
import javafx.util.Callback;
import at.model.Model;
import at.db.DBUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import at.model.WindowUtil;

public class MainViewController implements Initializable, Observer {
    @FXML
    private ComboBox<String> cbClass;
    @FXML
    private ListView<Student> lvStudent;
    @FXML
    private Label lbName;
    @FXML
    private Label lbClass;
    @FXML
    private Label lbBirthDate;
    @FXML
    private TextField tfListViewSearch;
    @FXML
    private ListView lvHistory;
    @FXML
    private ListView lvCheckupHistory;

    private Model model;
    
    //TODO: create background thread which checks the connection to the db

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = Model.getModel();
        model.addObserver(this);

        //adds all the classes found in the db to the ComboBox cbClass
        cbClass.setItems(Model.getModel().getAllClasses());

        //Set the Items of the listView
        lvStudent.setItems(model.getStudentList().sorted(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getLastName().toUpperCase().compareTo(o2.getLastName().toUpperCase());
            }
        }));
        lvHistory.setItems(model.getStudentDocumentList());
        lvCheckupHistory.setItems(model.getStudentCheckups());
        lvCheckupHistory.setCellFactory(new Callback<ListView<Checkup>, ListCell<Checkup>>(){

            @Override
            public ListCell<Checkup> call(ListView<Checkup> p) {

                ListCell<Checkup> cell = new ListCell<Checkup>(){

                    @Override
                    protected void updateItem(Checkup c, boolean bln) {
                        super.updateItem(c, bln);

                        if (c != null) {
                            setText(c.getTimeStamp() + ": " + c.getHeight() + "cm / " + c.getWeight() + "kg -> " + Model.getModel().calcBmi(c.getHeight(), c.getWeight()) + " BMI");
                        }
                    }
                };
                return cell;
            }
        });


        //Updates the List
        model.updateList();

        //region Listeners of the searchBars and class selection
        cbClass.valueProperty().addListener((observable, oldValue, newValue) -> {
            model.sortList(tfListViewSearch.getText(), newValue);
        });

        tfListViewSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            model.sortList(newValue, cbClass.valueProperty().get());
        });
        //endregion

        //region ChangeListener of lvStudent
        lvStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lbName.setText(newValue.getFirstname() + " " + newValue.getLastName());
                lbClass.setText(newValue.getClazz());
                lbBirthDate.setText(newValue.getBirthdate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + "  (" + Period.between(newValue.getBirthdate(),LocalDate.now()).getYears() + ")");
                model.updateStudentDocuments(newValue.getId());
                lvCheckupHistory.refresh();
                lvCheckupHistory.setItems(null);
                lvCheckupHistory.setItems(Model.getModel().getStudentCheckups());
                lvCheckupHistory.refresh();
            }
            else {
                lbName.setText("");
                lbClass.setText("");
                lbBirthDate.setText("");
                lvHistory.getItems().clear();
                lvCheckupHistory.setItems(null);
            }
        });
        //endregion

    }


    @Override
    public void update(Observable o, Object arg) {
        Student currentStudent = lvStudent.getSelectionModel().getSelectedItem();
        if (currentStudent != null) {
            //Checks if the data of the student has changed
            lbName.setText(currentStudent.getFirstname() + " " + currentStudent.getLastName());
            lbClass.setText(currentStudent.getClazz());
            lbBirthDate.setText(currentStudent.getBirthdate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + "  (" + Period.between(currentStudent.getBirthdate(), LocalDate.now()).getYears() + ")");

            //Updates the student listView when current selected item has changed
            //lvStudent.getItems().set(lvStudent.getSelectionModel().getSelectedIndex(), currentStudent);
            lvStudent.setItems(Model.getModel().getStudentList().sorted(new Comparator<Student>() {
                @Override
                public int compare(Student o1, Student o2) {
                    return o1.getLastName().toUpperCase().compareTo(o2.getLastName().toUpperCase());
                }
            }));

            //Updates the history
            model.getModel().updateStudentDocuments(currentStudent.getId());
        }
    }

    //region MenuBar Events
    @FXML
    private void onImport(MouseEvent event) {
        FileChooser f = new FileChooser();

        File file = f.showOpenDialog(cbClass.getScene().getWindow());
        if (file != null) {
            if (file.getPath().contains(".csv")) {
                Model.getModel().insertStudentFromCSV(file);
                cbClass.setItems(Model.getModel().getAllClasses());
            }
        }
    }

    @FXML
    private void onInsert(MouseEvent event) {
        Parent root;
        String cssFile ="";
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/NewStudent.fxml"));
            Scene scene = new Scene(root);

            Stage stage = new Stage(StageStyle.DECORATED);
            if (cssFile.equals("")) {
                scene.getStylesheets().add("/styles_visuals/document.css");
            }
            else {
                scene.getStylesheets().add("/styles_visuals/" + cssFile + ".css");
            }
            stage.getIcons().add(new Image("/styles_visuals/dh_logo.png"));
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setAlwaysOnTop(true);
            stage.initStyle(StageStyle.UTILITY);
            stage.centerOnScreen();
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    cbClass.setItems(Model.getModel().getAllClasses());
                    lvCheckupHistory.setItems(null);
                }
            });
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onDelete(MouseEvent event) {
        if (lvStudent.getSelectionModel().getSelectedItem() != null) {

            Alert securityQuestion = new Alert(Alert.AlertType.CONFIRMATION);
            securityQuestion.setContentText("Wirklich löschen?");
            securityQuestion.showAndWait();
            if (securityQuestion.getResult() == ButtonType.OK) {
                model.deleteStudent(lvStudent.getSelectionModel().getSelectedItem());
                lvStudent.getSelectionModel().select(null);
                lbName.setText("");
                lbBirthDate.setText("");
                lbClass.setText("");
                cbClass.setItems(Model.getModel().getAllClasses());
            }
        }
        else {
            WindowUtil.showInfoDialog("Kein Schüler ausgewählt!");
        }
    }

    @FXML
    private void onUpdate(MouseEvent event) {
        if (lvStudent.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UpdateStudent.fxml"));
                Scene scene = new Scene(loader.load());
                UpdateStudentController controller = loader.getController();

                controller.init(lvStudent.getSelectionModel().getSelectedItem());
                openScene(scene, "");
            } catch (IOException ex) {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else{
            WindowUtil.showInfoDialog("Kein Schüler ausgewählt!");
        }
    }

    @FXML
    private void onNextYear(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NextYear.fxml"));
            Scene scene = new Scene(loader.load());
            NextYearController controller = loader.getController();

            controller.init();
            openScene(scene, "");
            scene.getWindow().setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    cbClass.setItems(null);
                    cbClass.setItems(Model.getModel().getAllClasses());
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onAbout(MouseEvent event) {
        String about = "Applikation erstellt von: \nScheinecker Tobias\nStarzengruber Benedikt\nWickenhauser Lukas\n\nWeitere Beteiligte:\nSchöffmann Alexander\nSteininger Philipp\nStürzlinger Florian";
        WindowUtil.showMessageDialog(about);
    }

    @FXML
    private void onLogout(MouseEvent event) {
        try {
            cbClass.getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginView.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles_visuals/login.css");
            stage.getIcons().add(new Image("/styles_visuals/dh_logo.png"));
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED); //No Windows-Window-Shit
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void onExit(MouseEvent event) {
        DBUtil.shutdown();
        System.exit(0);
    }
    //endregion

    //region Methods for adding diseases, gymreleases, parentMessages, height, weight and reports to a specific student
    @FXML
    private void onGymRelease(MouseEvent event) {
        if (lvStudent.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Gymrelease.fxml"));
                Scene scene = new Scene(loader.load());
                GymreleaseController controller = loader.getController();

                controller.init(lvStudent.getSelectionModel().getSelectedItem(), null);
                openScene(scene, "");
            } catch (IOException ex) {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else{
            WindowUtil.showInfoDialog("Kein Schüler ausgewählt!");
        }
    }

    @FXML
    private void onDiseaseReport(MouseEvent event) {
        if (lvStudent.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Disease.fxml"));
                Scene scene = new Scene(loader.load());
                DiseaseController controller = loader.getController();

                controller.init(lvStudent.getSelectionModel().getSelectedItem(), null);
                openScene(scene, "");
            } catch (IOException ex) {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else{
            WindowUtil.showInfoDialog("Kein Schüler ausgewählt!");
        }
    }

    @FXML
    private void onParentMessage(MouseEvent event) {
        if (lvStudent.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ParentMessage.fxml"));
                Scene scene = new Scene(loader.load());
                ParentMessageController controller = loader.getController();

                controller.init(lvStudent.getSelectionModel().getSelectedItem(), null);
                openScene(scene, "");
            } catch (IOException ex) {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else{
            WindowUtil.showInfoDialog("Kein Schüler ausgewählt!");
        }
    }

    @FXML
    private void onCheckup(MouseEvent event){

        if (lvStudent.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Checkup.fxml"));
                Scene scene = new Scene(loader.load());
                CheckupController controller = loader.getController();

                controller.init(lvStudent.getSelectionModel().getSelectedItem(), null);
                openScene(scene, "");
            } catch (IOException ex) {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
            WindowUtil.showInfoDialog("Kein Schüler ausgewählt");
    }

    public void onTrip(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Trip.fxml"));
            Scene scene = new Scene(loader.load());
            TripController controller = loader.getController();

            controller.init();
            openScene(scene, "");
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void lvHistoryClicked(MouseEvent event) throws IOException {
        if (lvStudent.getSelectionModel().getSelectedItem() == null)
            return;

        //if the history was clicked two times it will reopen the Document for editing
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            Object document = lvHistory.getSelectionModel().getSelectedItem();
            if (document.getClass().equals(Disease.class)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Disease.fxml"));
                Scene scene = new Scene(loader.load());

                DiseaseController controller = loader.getController();
                controller.init(lvStudent.getSelectionModel().getSelectedItem(), (Disease)lvHistory.getSelectionModel().getSelectedItem());

                openScene(scene, "");
            }
            else if (document.getClass().equals(Gymrelease.class)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Gymrelease.fxml"));
                Scene scene = new Scene(loader.load());

                GymreleaseController controller = loader.getController();
                controller.init(lvStudent.getSelectionModel().getSelectedItem(), (Gymrelease)lvHistory.getSelectionModel().getSelectedItem());

                openScene(scene, "");
            }
            else if (document.getClass().equals(ParentMessage.class)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ParentMessage.fxml"));
                Scene scene = new Scene(loader.load());

                ParentMessageController controller = loader.getController();
                controller.init(lvStudent.getSelectionModel().getSelectedItem(), (ParentMessage)lvHistory.getSelectionModel().getSelectedItem());

                openScene(scene, "");
            }
            else if (document.getClass().equals(Checkup.class)) {
                loadCheckupScene(lvStudent.getSelectionModel().getSelectedItem(), (Checkup) lvHistory.getSelectionModel().getSelectedItem());
            }
        }
    }

    private void openScene(Scene scene, String cssFile) {
        Stage stage = new Stage(StageStyle.DECORATED);
        if (cssFile.equals("")) {
            scene.getStylesheets().add("/styles_visuals/document.css");
        }
        else {
            scene.getStylesheets().add("/styles_visuals/" + cssFile + ".css");
        }
        stage.getIcons().add(new Image("/styles_visuals/dh_logo.png"));
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setAlwaysOnTop(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.centerOnScreen();
        stage.show();
    }


    public void lvCheckupHistoryClicked(MouseEvent event) throws IOException {
        if (lvStudent.getSelectionModel().getSelectedItem() == null)
            return;

        //if the history was clicked two times it will reopen the Document for editing
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            Object document = lvCheckupHistory.getSelectionModel().getSelectedItem();

            if (document != null){
                loadCheckupScene(lvStudent.getSelectionModel().getSelectedItem(), (Checkup)lvCheckupHistory.getSelectionModel().getSelectedItem());
            }
        }
    }

    private void loadCheckupScene(Student student, Checkup checkup)throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Checkup.fxml"));
        Scene scene = new Scene(loader.load());

        CheckupController controller = loader.getController();
        controller.init(student, checkup);

        openScene(scene, "");
    }


    public void lvStudentClicked(MouseEvent event) throws IOException {
        if (lvStudent.getSelectionModel().getSelectedItem() == null)
            return;

        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            onUpdate(null);
        }
    }



    //endregion
}
