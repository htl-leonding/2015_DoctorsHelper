package at.controller;

/**
 * Created by benes on 04.03.2016.
 */

import at.db.DbUsersController;
import at.db.DBUtil;
import at.entity.Member;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import at.model.WindowUtil;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    private DbUsersController dbUser;
    private EntityManager em;

    @FXML
    private ListView<Member> lvUsers;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            dbUser = DbUsersController.getInstance();
            em = DBUtil.getEntityManager();
            lvUsers.setItems(FXCollections.observableList(dbUser.getAllUsers()));
        } catch (Exception e) {
        }
    }

    private Pair<String, String> showUserDialog(String username) {

        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("User");
        dialog.setHeaderText("Add or edit user");


        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField user = new TextField();
        if (username != null){
            user.setText(username);
            user.setDisable(true);
        } else user.setPromptText("Username");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(user, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        user.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!password.getText().isEmpty())
                loginButton.setDisable(newValue.trim().isEmpty());
        });

        password.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!user.getText().isEmpty())
                loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> user.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(user.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        if (result.isPresent()){
            return result.get();
        } else return null;
    }

    public void onCreateUser(Event event) {
        Pair<String, String> result = showUserDialog(null);
        if (result != null){
            dbUser.addUser(new Member(result.getKey(), result.getValue()));
            lvUsers.setItems(FXCollections.observableList(dbUser.getAllUsers()));
        }
    }

    @FXML
    public void onChangePassword(Event event) {
        if (lvUsers.getSelectionModel().getSelectedItem() != null) {
            Pair<String,String> result = showUserDialog(lvUsers.getSelectionModel().getSelectedItem().getUsername());
            if (result != null) {
                dbUser.changeUser(lvUsers.getSelectionModel().getSelectedItem(), new Member(result.getKey(), result.getValue()));
            }
        } else WindowUtil.showInfoDialog("Kein Bentuzer ausgewählt");
    }

    public void onDeleteUser(Event event) {
        Member m = lvUsers.getSelectionModel().getSelectedItem();

        if (m != null){
            if (!m.getUsername().equals("admin")){
                Alert securityQuestion = new Alert(Alert.AlertType.CONFIRMATION);
                securityQuestion.setContentText("Wirklich löschen?");
                securityQuestion.showAndWait();
                if (securityQuestion.getResult() == ButtonType.OK) {
                    dbUser.deleteUser(m);
                    lvUsers.setItems(FXCollections.observableList(dbUser.getAllUsers()));
                }
            } else WindowUtil.showInfoDialog("Administrator kann nicht gelöscht werden");
        } else WindowUtil.showInfoDialog("Kein Benutzer ausgewählt");
    }
}
