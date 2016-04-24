package com.schooldoctor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Schooldoctor extends Application {

    /**
     * Shows the login view
     * @param stage
     * @throws Exception 
     */
    //public static NetworkServerControl serverControl;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginView.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles_visuals/login.css");
        stage.getIcons().add(new Image("/styles_visuals/dh_logo.png"));
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED); //No Windows-Window
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
        stage.requestFocus();
       /* try {

            if(System.getProperty("os.name").contains("Windows")){
                System.out.println(System.getProperty("user.dir") );
                Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\dbStart.bat");

            }
            if(System.getProperty("os.name").contains("OSX")){
                //TODO: start os x dbStart script

            }
            if(System.getProperty("os.name").contains(("Linux"))){
                //TODO: start linux dbStart script
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }*/
    }

    public static void main(String[] args) {
        launch(args);
    }

}
