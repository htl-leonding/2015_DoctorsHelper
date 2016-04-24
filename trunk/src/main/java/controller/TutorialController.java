/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class TutorialController implements Initializable {
    @FXML
    private Pagination pagination; 

    private LinkedList<Image> images;
    private LinkedList<String> imageDescriptions;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        images = new LinkedList<>();
        imageDescriptions = new LinkedList<>();

        //Add the Images
        images.add(new Image("styles_visuals/tutorial02.png"));
        imageDescriptions.add("Einfügen von Schülern");

        //Sets the Images
        pagination.setPageFactory(param -> {
            if (param >= images.size()) {
                return new Label("Kein Bild verfügbar");
            }
            return createPage(param);
        });
    }

    private StackPane createPage(int index) {
        StackPane pane = new StackPane();

        ImageView imageView = new ImageView(images.get(index));
        imageView.setFitWidth(660);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        Label label = new Label(imageDescriptions.get(index));
        label.setStyle("-fx-text-fill: #0072C6; -fx-font-size: 20px;");

        pane.getChildren().addAll(label, imageView);
        pane.setAlignment(label, Pos.TOP_CENTER);
        pane.setAlignment(imageView, Pos.BOTTOM_CENTER);

        return pane;
    }
}
