package model;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * Created by Phips on 13.08.2015.
 */
public class WindowUtil {

    //Checks the given Controls
    public static boolean checkValid(List<Control> normalControls, List<Control> dateControls) {
        boolean notNull = checkNotNull(normalControls);
        boolean dateValid = checkDate(dateControls);



        if (notNull && dateValid) return true;
        return false;
    }

    //Checks if the controls are not null
    public static boolean checkNotNull(List<Control> controls) {
        boolean wrongInput = false;
        for (Control c : controls) {
            if (c.getClass().equals(TextField.class)) {
                if (((TextField)c).getText().equals("")) {
                    changeBorderColor(c, Color.RED);
                    wrongInput = true;
                }
                else {
                    changeBorderColor(c, Color.web("#BABABA"));
                }
            }
            else if (c.getClass().equals(TextArea.class)) {
                if (((TextArea)c).getText().equals("")) {
                    changeBorderColor(c, Color.RED);
                    wrongInput = true;
                }
                else {
                    changeBorderColor(c, Color.web("#BABABA"));
                }
            }
            else if (c.getClass().equals(DatePicker.class)) {
                if (((DatePicker)c).getValue() == null) {
                    changeBorderColor(c, Color.RED);
                    wrongInput = true;
                }
                else {
                    changeBorderColor(c, Color.web("#BABABA"));
                }
            }
            else if (c.getClass().equals(ComboBox.class)) {
                if (((ComboBox)c).getValue() == null) {
                    changeBorderColor(c, Color.RED);
                    wrongInput = true;
                }
                else {
                    changeBorderColor(c, Color.web("#BABABA"));
                }
            }
        }
        if (wrongInput) return false;
        return true;
    }

    //Checks the date of the controls
    public static boolean checkDate(List<Control> controls) {
        boolean wrongInput = false;
        for (Control c : controls) {
            if (c.getClass().equals(TextField.class) || c.getClass().equals(TextArea.class)) {
                if (!((TextField) c).getText().matches("([0][1-9]|[12][0-9]|[3][012])\\.([1][012]|[0][1-9])\\.([0-9]{4})")) {
                    changeBorderColor(c, Color.RED);
                    wrongInput = true;
                } else {
                    changeBorderColor(c, Color.web("#BABABA"));
                }
            }
        }

        if (wrongInput) return false;
        return true;
    }

    private static void changeBorderColor(Control c, Color color) {
        Platform.runLater(() -> c.setStyle("-fx-border-color: " + Integer.toHexString(color.hashCode()).substring(0, 6).toUpperCase()));
    }

    public static void showInfoDialog(String message){
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setContentText(message);
        infoAlert.showAndWait();
    }

    public static void setLabelMessageAnimation(Label l, String message) {
        Thread t = new Thread(() -> {
            Platform.runLater(() -> {
                l.setOpacity(1);
                l.setText(message);
            });

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (double i = l.getOpacity(); i > 0; i -= 0.05) {
                final double opacity = i;
                Platform.runLater(() -> l.setOpacity(opacity));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

}
