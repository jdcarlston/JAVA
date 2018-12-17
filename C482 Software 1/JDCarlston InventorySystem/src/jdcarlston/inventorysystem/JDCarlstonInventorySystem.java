/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdcarlston.inventorysystem;

import Model.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * C482 Software 1 Performance Assessment Inventory Management System Code GYP1
 *
 * @author Jennifer Carlston
 */
public class JDCarlstonInventorySystem extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        loadInventory();

        //Show Main Screen
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View_Controller/MainScreen.fxml"));
        AnchorPane screen = (AnchorPane) loader.load();

        Scene scene = new Scene(screen);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void loadInventory() {

        //Parts
        InhousePart pa1 = new InhousePart(1, "IHPa1", 1.00, 1, 1, 1, 1);
        InhousePart pa2 = new InhousePart(2, "IHPa2", 2.00, 1, 1, 3, 2);

        Inventory.addPart(pa1);
        Inventory.addPart(pa2);

        ObservableList<Part> parts = FXCollections.observableArrayList();
        parts.add(pa1);

        //Products
        Product pr1 = new Product(1, "Pr1", 23.00, 1, 1, 1);
        Product pr2 = new Product(2, "Pr2", 32.00, 5, 1, 5);

        Inventory.addProduct(pr1);
        //pr1.addAssociatedPart(pa1);

        Inventory.addProduct(pr2);
        pr2.addAssociatedPart(pa1);
        pr2.addAssociatedPart(pa2);
    }
}
