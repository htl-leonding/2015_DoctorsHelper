package controller;

import db.DBUtil;
import db.DbUsersController;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import entity.Member;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.persistence.EntityManager;

/**
 * FXML controller class Manages the login. Check the input and lead to the
 * mainview
 *
 * @author Bene
 */
public class LoginController implements Initializable {

    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Label lbErrorMessage;
    @FXML
    private Button btnLogin;

    @FXML
    private Label lbDisconnected;
    @FXML
    private Label lbReconnect;
    @FXML
    private ImageView ivLoading;

    private DbUsersController dbUser;
    private EntityManager em;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        try {
            initializeAdminUser();
        }
        catch (Exception e) {
            reconnect();
        }
    }

    private void reconnect() {
        switchView("reconnect");

        //region Init reconnectTask
        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while(em == null) {
                    try {
                        for (int i = 5; i >= 0; i--) {
                            updateMessage("Verbindungs in " +  i + " Sekunden");
                            Thread.sleep(1000);
                        }
                        updateMessage("Verbinde");
                        em = DBUtil.getEntityManager();
                    }
                    catch (Exception e1) {
                        return null;
                    }
                }
                return null;
            }
        };

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                try {
                    initializeAdminUser();
                }
                catch (Exception e) {
                    reconnect();
                }
            }
        });

        lbReconnect.textProperty().bind(task.messageProperty());
        //endregion

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    //If button "Anmelden" is pressed, check if the username and password is
    //right and lead to the mainview (or show an error message)
    @FXML
    private void onButtonLogin(ActionEvent event) {
        if (em != null) {
            int code = dbUser.validateUser(tfUsername.getText(), tfPassword.getText());
            //userData --> continue to mainWindow
            if (code == 1) {
                initializeMainView();
                tfPassword.getScene().getWindow().hide();
            }
            //userData wrong --> setMessage and retry
            else if (code == 0){
                lbErrorMessage.setOpacity(1.0);
                lbErrorMessage.setText("Anmeldedaten nicht korrekt");
                Timer errorTextTimer = new Timer();
                errorTextTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            lbErrorMessage.setOpacity(lbErrorMessage.getOpacity() - 0.05);
                            if (lbErrorMessage.getOpacity() <= 0) {
                                errorTextTimer.cancel();
                            }
                        });
                    }
                }, 1500, 10);
            }
            //Connection error --> reconnecting
            else {
                DBUtil.shutdown();
                em = null;
                reconnect();
            }
        }
    }

    //Initializes all components for the next view, so the change after the login is fast
    private void initializeMainView() {
        stage = new Stage();
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
            scene = new Scene(root);

            scene.getStylesheets().add("/styles_visuals/mainview.css");
            stage.setScene(scene);
            stage.getIcons().add(new Image("/styles_visuals/dh_logo.png"));
            stage.setResizable(false);
            stage.centerOnScreen();

            stage.setOnCloseRequest(event -> System.exit(1));

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //If the 'x' is pressed, the program closes
    @FXML
    private void onExit(MouseEvent event) {
        System.exit(0);
    }

    //If you press enter at the password textfield it has the same result as clicking 'anmelden'
    @FXML
    private void onPasswordEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER){
                onButtonLogin(null);
        }
    }

    //switches the LoginWindow between the tryToReconnectView and the LoginView
    private void switchView(String view) {
        if (view.equals("reconnect")) {
            tfUsername.setOpacity(0);
            tfPassword.setOpacity(0);
            btnLogin.setOpacity(0);

            lbDisconnected.setOpacity(1);
            lbReconnect.setOpacity(1);
            ivLoading.setOpacity(1);
        }
        else if (view.equals("login")) {
            tfUsername.setOpacity(1);
            tfPassword.setOpacity(1);
            btnLogin.setOpacity(1);

            lbDisconnected.setOpacity(0);
            lbReconnect.setOpacity(0);
            ivLoading.setOpacity(0);
        }
    }

    public void onAdminLogin(Event event) {

        int loginResult = 0;
        if (tfUsername.getText().equals("admin")){
            loginResult = dbUser.validateUser(tfUsername.getText(), tfPassword.getText());
        }

        if (loginResult == 1){
            //Login erfolgreich

            try{
                final Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/AdminLogin.fxml"));
                Scene scene = new Scene(root);
                stage.getIcons().add(new Image("/styles_visuals/dh_logo.png"));
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.centerOnScreen();
                stage.show();
            } catch (IOException e){
                e.printStackTrace();
            }


        } else{
            //Login nicht erfolgreich
            lbErrorMessage.setOpacity(1.0);
            lbErrorMessage.setText("Anmeldedaten nicht korrekt");
            Timer errorText = new Timer();
            errorText.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        lbErrorMessage.setOpacity(lbErrorMessage.getOpacity() - 0.05);
                        if (lbErrorMessage.getOpacity() <= 0) {
                            errorText.cancel();
                        }
                    });
                }
            }, 1500, 10);
        }


    }

    private void initializeAdminUser(){
        dbUser = DbUsersController.getInstance();
        em = DBUtil.getEntityManager();

        List<Member> members = dbUser.getAllUsers();

        if (members.size() == 0){
            dbUser.addUser(new Member("admin", "admin"));
        }

        switchView("login");
    }
}
