/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.*;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Jennifer Carlston
 */
public abstract class BaseController implements Initializable {
 
    /**
     *
     * @param event
     * @param screenLoc
     * @throws IOException
     */
    public void showFxScreen(ActionEvent event, String screenLoc) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(screenLoc));
        AnchorPane screen = (AnchorPane) loader.load();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(screen);

        window.setScene(scene);
        window.show();
    }
    
    /**
     *
     * @param title
     * @param msg
     */
    public static void alertInvalid(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Validation Error");
        alert.setHeaderText("Invalid " + title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     *
     * @param itemType
     */
    public static void alertSearch(String itemType) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Search Error");
        alert.setHeaderText("Not found");
        alert.setContentText("The search term entered does not match any known " + itemType.toLowerCase() + ".");
        alert.showAndWait();
    }
}
